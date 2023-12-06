package com.techworld.order;

import com.techworld.Utility;
import com.techworld.common.entity.*;
import com.techworld.customer.CustomerService;
import com.techworld.review.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
public class OrderController {
    @Autowired private OrderService orderService;
    @Autowired private CustomerService customerService;
    @Autowired private ReviewService reviewService;

    private Customer getAuthenticatedCustomer(HttpServletRequest request) {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        return customerService.getCustomerByEmail(email);
    }

    @GetMapping("/orders/detail/{id}")
    public String viewOrderDetails(Model model, HttpServletRequest request, @PathVariable(name = "id") Integer id){
        Customer customer = getAuthenticatedCustomer(request);
        Order order = orderService.getOrder(id,customer);

        setProductReviewableStatus(customer, order);
        model.addAttribute("order",order);

        return "customer/order_details_modal";
    }

    private void setProductReviewableStatus(Customer customer, Order order) {
        Iterator<OrderDetail> iterator = order.getOrderDetails().iterator();

        while (iterator.hasNext()){
            OrderDetail orderDetail = iterator.next();
            Product product = orderDetail.getProduct();
            Integer productId = product.getId();

            boolean disCustomerReviewProduct = reviewService.didCustomerReviewProduct(customer, productId);
            product.setReviewByCustomer(disCustomerReviewProduct);

            if(!disCustomerReviewProduct){
                boolean canCustomerReviewProduct = reviewService.canCustomerReviewProduct(customer, productId);
                product.setCustomerCanReview(canCustomerReviewProduct);
            }
        }
    }


}
