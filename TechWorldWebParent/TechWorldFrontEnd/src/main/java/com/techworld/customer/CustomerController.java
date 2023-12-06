package com.techworld.customer;

import com.techworld.Utility;
import com.techworld.address.AddressService;
import com.techworld.common.entity.Address;
import com.techworld.common.entity.Customer;
import com.techworld.common.entity.Order;
import com.techworld.order.OrderService;
import com.techworld.security.CustomerUserDetails;
import com.techworld.security.oauth.CustomerOauth2User;
import com.techworld.security.oauth.CustomerOauth2UserService;
import com.techworld.setting.EmailSettingBag;
import com.techworld.setting.SettingService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class CustomerController {
    @Autowired private CustomerService customerService;
    @Autowired private SettingService settingService;
    @Autowired private AddressService addressService;
    @Autowired private OrderService orderService;

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("pageTitle","Customer Registration");
        model.addAttribute("customer", new Customer());

        return "register/register_form";
    }

    @PostMapping("/create_customer")
    public String createCustomer(Customer customer, Model model, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        customerService.registerCustomer(customer);
        sendVerificationEmail(request,customer);

        model.addAttribute("pageTitle","Registration Succeeded!");
        return "/register/register_success";
    }

    private void sendVerificationEmail(HttpServletRequest request, Customer customer) throws MessagingException, UnsupportedEncodingException {
        EmailSettingBag emailSettingBag = settingService.getEmailSettings();
        JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettingBag);

        String toAddress = customer.getEmail();
        String subject = emailSettingBag.getCustomerVerifySubject();
        String content = emailSettingBag.getCustomerVerifyContent();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(emailSettingBag.getForm(),emailSettingBag.getSenderName());
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]",customer.getFullName());
        String verifyURL = Utility.getSiteURL(request) + "/verify?code=" + customer.getVerificationCode();

        content = content.replace("[[URL]]",verifyURL);
        helper.setText(content,true);
        mailSender.send(message);
    }

    @GetMapping("/verify")
    public String verifyAccount(@Param("code") String code, Model model){
        boolean verified = customerService.verify(code);

        return "register/"+(verified ? "verify_success" : "verify_fail");
    }

    @GetMapping("/acount_details")
    public String viewAccountDetails(Model model, HttpServletRequest request){
        String email = getEmailOfAuthenticatedCustomer(request);
        Customer customer = customerService.getCustomerByEmail(email);
        Address address = addressService.getDefaultAddress(customer);
        List<Order> listOrders = orderService.listForCustomer(customer);

        model.addAttribute("address",address);
        model.addAttribute("customer",customer);
        model.addAttribute("listOrders",listOrders);
        return "customer/account_form";
    }

    private String getEmailOfAuthenticatedCustomer(HttpServletRequest request){
        Object principal = request.getUserPrincipal();
        String customerEmail = null;

        if (principal instanceof UsernamePasswordAuthenticationToken || principal instanceof RememberMeAuthenticationToken){
            customerEmail = request.getUserPrincipal().getName();
        }else if(principal instanceof OAuth2AuthenticationToken){
            OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) principal;
            CustomerOauth2User oauth2User = (CustomerOauth2User)oauth2Token.getPrincipal();
            customerEmail = oauth2User.getEmail();
        }

        return customerEmail;
    }

    @PostMapping("/update_acount_details")
    public String updateAccountDetails(Model model, Customer customer, RedirectAttributes rs, HttpServletRequest request){
        customerService.update(customer);
        rs.addFlashAttribute("message","Tài khoản của bạn đã được cập nhật thành công!");
        updateNameForAuthenticatedCustomer(customer, request);
        return "redirect:/acount_details";
    }

    private void updateNameForAuthenticatedCustomer(Customer customer, HttpServletRequest request) {
        Object principal = request.getUserPrincipal();

        if (principal instanceof UsernamePasswordAuthenticationToken || principal instanceof RememberMeAuthenticationToken){
            CustomerUserDetails userDetails = getCustomeruserDetailsobject(principal);
            Customer cauthenticatedCustomer = userDetails.getCustomer();
            cauthenticatedCustomer.setFullName(customer.getFullName());
        }else if(principal instanceof OAuth2AuthenticationToken){
            OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) principal;
            CustomerOauth2User oauth2User = (CustomerOauth2User)oauth2Token.getPrincipal();
            oauth2User.setFullName(customer.getFullName());
        }
    }

    private CustomerUserDetails getCustomeruserDetailsobject(Object principal){
        CustomerUserDetails userDetails = null;
        if (principal instanceof  UsernamePasswordAuthenticationToken){
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
            userDetails = (CustomerUserDetails) token.getPrincipal();
        } else if(principal instanceof  RememberMeAuthenticationToken){
            RememberMeAuthenticationToken token = (RememberMeAuthenticationToken) principal;
            userDetails = (CustomerUserDetails) token.getPrincipal();
        }

        return userDetails;
    }
}
