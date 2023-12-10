package com.techworld.product;

import com.techworld.Utility;
import com.techworld.brand.BrandService;
import com.techworld.category.CategoryService;
import com.techworld.common.entity.*;
import com.techworld.customer.CustomerNotFoundException;
import com.techworld.customer.CustomerService;
import com.techworld.question.QuestionService;
import com.techworld.review.ReviewService;
import com.techworld.review.vote.ReviewVoteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CustomerService customerService;
    @Autowired
    private ReviewVoteService reviewVoteService;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/shopping")
    public String viewShoppingProduct(Model model){
        List<Category> listCategories = categoryService.listCategories();
        List<Product> listProducts = productService.findOneProductForCategory(listCategories);
        List<Brand> listBrands = brandService.listAll();

        model.addAttribute("listCategories",listCategories);
        model.addAttribute("listProducts",listProducts);
        model.addAttribute("listBrands",listBrands);

        return "shop";
    }

    @GetMapping("/p/{product_alias}")
    public String viewProductDetail(@PathVariable("product_alias") String alias, Model model, HttpServletRequest request){
        try{
            Product product = productService.getProduct(alias);
            List<Product> listProduct = productService.findProductByCategory(product.getCategory().getId());

            List<Review> listReviews = reviewService.listReviewByProduct(product);

            Customer customer = getAuthenticatsCustomer(request);

            List<Question> listQuestions = questionService.listQuestionByProduct(product);

            if(customer != null){
                reviewVoteService.markReviewsVotedForProductByCustomer(listReviews, product.getId(), customer.getId());
                Question question = new Question();
                model.addAttribute("question",question);
            }

            model.addAttribute("product",product);
            model.addAttribute("listReviews",listReviews);
            model.addAttribute("pageTitle",product.getShortName());
            model.addAttribute("listProduct",listProduct);
            model.addAttribute("listQuestions",listQuestions);

            return "product_detail";
        }catch (ProductNotFoundException e){
            return "error/404";
        } catch (CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/search")
    public String searchProduct(@Param("keyword") String keyword, Model model, RedirectAttributes rs){
        List<Product> listProducts = productService.listProductBySearchKeyWord(keyword);
        List<Category> listCategories = categoryService.listCategories();
        List<Brand> listBrands = brandService.listAll();

        model.addAttribute("listCategories",listCategories);
        model.addAttribute("listBrands",listBrands);
        if(listProducts.isEmpty()){
            model.addAttribute("message","Không tìm thấy sản phẩm có liên quan");
        }else{
            model.addAttribute("listProducts",listProducts);
        }
        return "shop";
    }

    @GetMapping("/c/{name}")
    public String listProductByCategoryName(@PathVariable("name") String name, Model model){
        List<Product> listProducts = productService.listProductByCategoryName(name);
        List<Category> listCategories = categoryService.listCategories();
        List<Brand> listBrands = brandService.listAll();

        model.addAttribute("listCategories",listCategories);
        model.addAttribute("listProducts",listProducts);
        model.addAttribute("listBrands",listBrands);

        return "shop";
    }

    private Customer getAuthenticatsCustomer(HttpServletRequest request) throws CustomerNotFoundException {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        return customerService.getCustomerByEmail(email);
    }
}
