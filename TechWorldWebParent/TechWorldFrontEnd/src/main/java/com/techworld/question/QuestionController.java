package com.techworld.question;

import com.techworld.Utility;
import com.techworld.common.entity.Customer;
import com.techworld.common.entity.Product;
import com.techworld.common.entity.Question;
import com.techworld.customer.CustomerNotFoundException;
import com.techworld.customer.CustomerService;
import com.techworld.product.ProductNotFoundException;
import com.techworld.product.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class QuestionController {
    @Autowired private QuestionService questionService;
    @Autowired private CustomerService customerService;
    @Autowired private ProductService productService;

    @PostMapping("/question/save")
    public String saveQuestionPage(Question question, RedirectAttributes rs, HttpServletRequest request, Integer productId) throws CustomerNotFoundException, ProductNotFoundException {
        Customer customer = getAuthenticatsCustomer(request);
        Product product = productService.getProduct(productId);
        questionService.saveQuestion(question, customer, product);

        rs.addFlashAttribute("message","Câu hỏi của bạn đã được lưu lại và chờ sự phê duyệt của quản trị viên.");

        String alias = URLEncoder.encode(product.getAlias(), StandardCharsets.UTF_8);
        return "redirect:/p/" + alias;

    }

    private Customer getAuthenticatsCustomer(HttpServletRequest request) throws CustomerNotFoundException {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        return customerService.getCustomerByEmail(email);
    }
}
