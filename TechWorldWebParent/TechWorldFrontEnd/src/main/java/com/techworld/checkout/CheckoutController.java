package com.techworld.checkout;

import com.techworld.Utility;
import com.techworld.address.AddressService;
import com.techworld.cart.CartItemService;
import com.techworld.common.entity.*;
import com.techworld.config.VnPayConfig;
import com.techworld.customer.CustomerNotFoundException;
import com.techworld.customer.CustomerService;
import com.techworld.order.OrderService;
import com.techworld.setting.EmailSettingBag;
import com.techworld.setting.SettingService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Locale;

@Controller
public class CheckoutController {
    @Autowired private CheckoutService checkoutService;
    @Autowired private CustomerService customerService;
    @Autowired private AddressService addressService;
    @Autowired private CartItemService cartItemService;
    @Autowired private SettingService settingService;
    @Autowired private OrderService orderService;

    @GetMapping("/checkout")
    public String chowCheckoutPage(Model model, HttpServletRequest request) throws CustomerNotFoundException {
        Customer customer = getAuthenticatsCustomer(request);
        Address defaultAddress = addressService.getDefaultAddress(customer);
        if(defaultAddress != null){
            model.addAttribute("shippingAddress",defaultAddress.toString());
            model.addAttribute("defaultAddress",defaultAddress);
        }else{
            model.addAttribute("shippingAddress","Bạn chưa thiết lập địa chỉ giao hàng. Vui lòng thiết lập!");
            model.addAttribute("defaultAddress", null);
        }

        List<CartItem> cartItems = cartItemService.listCartItem(customer);
        CheckoutInfo checkoutInfo = checkoutService.prepareCheckout(cartItems);

        model.addAttribute("checkoutInfo", checkoutInfo);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("customer",customer);

        return "checkout/checkout";
    }

    private Customer getAuthenticatsCustomer(HttpServletRequest request) throws CustomerNotFoundException {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        return customerService.getCustomerByEmail(email);
    }

   @PostMapping("/place_order")
    public String placeOrder(HttpServletRequest request) throws CustomerNotFoundException, MessagingException, UnsupportedEncodingException {
        String paymentType = request.getParameter("paymentMethod");
        String note = request.getParameter("note");

        PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentType);

        Customer customer = getAuthenticatsCustomer(request);
        List<CartItem> cartItems = cartItemService.listCartItem(customer);
        CheckoutInfo checkoutInfo = checkoutService.prepareCheckout(cartItems);

        if(paymentType.equals("COD")){
            Address defaultAddress = addressService.getDefaultAddress(customer);
            Order createdOrder = orderService.createOrder(customer, defaultAddress, cartItems, paymentMethod, checkoutInfo, note);
            cartItemService.deleteByCustomer(customer);
            sendOrderConfirmationEmail(request, createdOrder);
            return "checkout/order_completed";
        }
        else{
            return createPayment(request,String.valueOf(checkoutInfo.getProductTotal()), note);
        }
    }

    private void sendOrderConfirmationEmail(HttpServletRequest request, Order order) throws MessagingException, UnsupportedEncodingException {
        EmailSettingBag emailSettingBags = settingService.getEmailSettings();
        JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettingBags);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi"));
        String formattedTotal = currencyFormatter.format(order.getTotal());
        mailSender.setDefaultEncoding("utf-8");

        String toAddress = order.getCustomer().getEmail();
        String subject = emailSettingBags.getOrderConfirmationSubject();
        String content = emailSettingBags.getOrderConfirmationContent();

        subject = subject.replace("[[orderId]]", String.valueOf(order.getId()));

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(emailSettingBags.getForm(), emailSettingBags.getSenderName());
        helper.setTo(toAddress);
        helper.setSubject(subject);

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss E, dd MMM yyyy");
        String orderTime = dateFormat.format(order.getOrderTime());

        content = content.replace("[[name]]",order.getCustomer().getFullName());
        content = content.replace("[[orderId]]", String.valueOf(order.getId()));
        content = content.replace("[[orderTime]]",orderTime);
        content = content.replace("[[shippingAddress]]",order.getShippingAddress());
        content = content.replace("[[paymentMethod]]", order.getPaymentMethod().toString());
        content = content.replace("[[total]]", formattedTotal+'đ');

        helper.setText(content,true);
        mailSender.send(message);
    }

    private String createPayment(HttpServletRequest request, String total, String note) throws UnsupportedEncodingException {
        String orderType = "other";
        double subTotal = Double.parseDouble(total);
        long amount = (long) (subTotal * 100);

        System.out.println(amount);
        String vnp_TxnRef = VnPayConfig.getRandomNumber(8);
        String vnp_IpAddr = VnPayConfig.getIpAddress(request);

        String vnp_TmnCode = VnPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VnPayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VnPayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        if(note == null || note.equals("")){
            vnp_Params.put("vnp_OrderInfo", "01");
        }else{
            vnp_Params.put("vnp_OrderInfo", note);
        }
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VnPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnPayConfig.hmacSHA512(VnPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VnPayConfig.vnp_PayUrl + "?" + queryUrl;

        return "redirect:" + paymentUrl;
    }

    @GetMapping("/payment_info")
    public String paymentSuccess(HttpServletRequest request, @RequestParam(value = "vnp_ResponseCode") String status, @RequestParam(value = "vnp_OrderInfo") String notes) throws CustomerNotFoundException, MessagingException, UnsupportedEncodingException {
        if(status.equals("00")){
            PaymentMethod paymentMethod = PaymentMethod.valueOf("VNPay");
            String note = (notes.equals("01") ? null : notes);

            Customer customer = getAuthenticatsCustomer(request);
            Address defaultAddress = addressService.getDefaultAddress(customer);
            List<CartItem> cartItems = cartItemService.listCartItem(customer);
            CheckoutInfo checkoutInfo = checkoutService.prepareCheckout(cartItems);

            Order createdOrder = orderService.createOrder(customer, defaultAddress, cartItems, paymentMethod, checkoutInfo,note);
            cartItemService.deleteByCustomer(customer);
            sendOrderConfirmationEmail(request, createdOrder);
            return "redirect:/payment_info/success";
        }else{
            return "redirect:/payment_info/fail";
        }
    }
//
    @GetMapping("/payment_info/success")
    public String paymentSuccess(){
        return "checkout/order_completed";
    }

    @GetMapping("/payment_info/fail")
    public String paymentFail(){
        return "checkout/order_fail";
    }
}
