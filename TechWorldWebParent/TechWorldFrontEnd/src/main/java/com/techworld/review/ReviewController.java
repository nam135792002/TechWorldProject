package com.techworld.review;

import com.techworld.Utility;
import com.techworld.common.entity.Customer;
import com.techworld.common.entity.Order;
import com.techworld.common.entity.Product;
import com.techworld.common.entity.Review;
import com.techworld.customer.CustomerNotFoundException;
import com.techworld.customer.CustomerService;
import com.techworld.order.OrderService;
import com.techworld.product.ProductNotFoundException;
import com.techworld.product.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReviewController {
    @Autowired private ProductService productService;
    @Autowired private CustomerService customerService;
    @Autowired private ReviewService reviewService;
    @Autowired private OrderService orderService;

    @GetMapping("/write_review/product/{productId}/{orderId}")
    public String showViewForm(@PathVariable("productId") Integer productId, @PathVariable("orderId") Integer orderId ,Model model, HttpServletRequest request) throws ProductNotFoundException, CustomerNotFoundException {
        Review review = new Review();
        Product product = null;
        try {
            product = productService.getProduct(productId);
        }catch (ProductNotFoundException ex){
            return "error/404";
        }

        Customer customer = getAuthenticatsCustomer(request);
        if(customer != null){
            boolean customerReviews = reviewService.didCustomerReviewProduct(customer, product.getId(), orderId);

            if(customerReviews){
                model.addAttribute("customerReviews", customerReviews);
            } else {
                boolean customerCanReview = reviewService.canCustomerReviewProduct(customer, product.getId(), orderId);
                if(customerCanReview){
                    model.addAttribute("customerCanReview",customerCanReview);
                }else{
                    model.addAttribute("NoReviewPermission",true);
                }
            }
        }

        model.addAttribute("product", product);
        model.addAttribute("review",review);
        model.addAttribute("order",orderService.getOrder(orderId,customer));
        return "/modal/review_form";
    }

    private Customer getAuthenticatsCustomer(HttpServletRequest request) throws CustomerNotFoundException {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        return customerService.getCustomerByEmail(email);
    }

    @PostMapping("/post_review")
    public String saveReview(Model model, Review review, Integer productId, Integer orderId, HttpServletRequest request) throws CustomerNotFoundException {
        Customer customer = getAuthenticatsCustomer(request);

        Product product = null;
        Order order = null;
        try {
            product = productService.getProduct(productId);
            order = orderService.getOrder(orderId,customer);
        }catch (ProductNotFoundException ex){
            return "error/404";
        }

        review.setProduct(product);
        review.setCustomer(customer);
        review.setOrder(order);

        Review savedReview = reviewService.save(review);

        model.addAttribute("review", savedReview);

        return "modal/review_done";
    }
}
