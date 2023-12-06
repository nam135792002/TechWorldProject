package com.techworld;

import com.techworld.category.CategoryService;
import com.techworld.common.entity.Category;
import com.techworld.common.entity.Product;
import com.techworld.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String viewHomePage(Model model){
        List<Category> listCategories = categoryService.listCategories();
        List<Product> listProducts = productService.findOneProductForCategory(listCategories);
        List<Product> listProductByDate = productService.findProductByDate();
        List<Product> listProductByDiscount = productService.findProductByDiscountPercent();
        model.addAttribute("listCategories",listCategories);
        model.addAttribute("listProducts",listProducts);
        model.addAttribute("listProductByDate",listProductByDate);
        model.addAttribute("listProductByDiscount",listProductByDiscount);
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
