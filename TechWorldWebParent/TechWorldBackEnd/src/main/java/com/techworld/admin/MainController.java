package com.techworld.admin;

import com.techworld.admin.brand.BrandService;
import com.techworld.admin.category.CategoryService;
import com.techworld.admin.customer.CustomerService;
import com.techworld.admin.order.OrderService;
import com.techworld.admin.product.ProductService;
import com.techworld.admin.review.ReviewService;
import com.techworld.admin.user.UserService;
import com.techworld.common.entity.Customer;
import com.techworld.common.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired private OrderService orderService;
    @Autowired private UserService userService;
    @Autowired private ReviewService reviewService;
    @Autowired private BrandService brandService;
    @Autowired private CategoryService categoryService;
    @Autowired private ProductService productService;
    @Autowired private CustomerService customerService;

    @GetMapping("/")
    public String viewHomePage(Model model){
        long orderCount = orderService.countOrder();
        model.addAttribute("orderCount",orderCount);

        int sumIncome = orderService.totalIncome();
        model.addAttribute("sumIncome",sumIncome);

        long countUser = userService.countUser();
        model.addAttribute("countUser",countUser);

        long countReview = reviewService.countReview();
        model.addAttribute("countReview", countReview);

        long countBrand = brandService.countBrand();
        model.addAttribute("countBrand",countBrand);

        long countCategory = categoryService.countCategory();
        model.addAttribute("countCategory", countCategory);

        long countProduct = productService.countProduct();
        model.addAttribute("countProduct", countProduct);

        long countCustomer = customerService.countCustomer();
        model.addAttribute("countCustomer", countCustomer);

        List<Order> listOrder = orderService.listOrderByNewStatus();
        model.addAttribute("listOrder", listOrder);

        Map<String, String> map = orderService.getValuePercent();
        model.addAttribute("map", map);

        List<Customer> listCustomers = customerService.listByPage();
        model.addAttribute("listCustomers",listCustomers);

        Map<String, String> map01 = orderService.getValuePayment();
        model.addAttribute("map01", map01);

        return "index";
    }

    @GetMapping("/login")
    public String viewLoginPage(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "login";
        }
        return "redirect:/";
    }
}
