package com.techworld.admin.review;

import com.techworld.admin.Message;
import com.techworld.common.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ReviewController {
    @Autowired private ReviewService reviewService;

    @GetMapping("/reviews")
    public String listFirstPage(Model model){
        List<Review> listReviews = reviewService.listByPage();

        model.addAttribute("listReviews",listReviews);
        return "reviews/reviews";
    }

    @GetMapping("/reviews/detail/{id}")
    public String viewReview(@PathVariable("id") Integer id, Model model, RedirectAttributes rs){
        try {
            Review review = reviewService.get(id);
            model.addAttribute("review",review);

            return "reviews/review_detail_modal";
        } catch (ReviewNotFoundException ex){
            rs.addFlashAttribute("message",new Message("error",ex.getMessage()));
            return "redirect:/reviews";
        }
    }

    @GetMapping("/reviews/edit/{id}")
    public String editReview(@PathVariable("id") Integer id, Model model, RedirectAttributes rs){
        try {
            Review review = reviewService.get(id);

            model.addAttribute("review",review);
            model.addAttribute("pageTitle", String.format("Edit Review (ID: %d)",id));

            return "reviews/review_form";
        } catch (ReviewNotFoundException ex){
            rs.addFlashAttribute("message",new Message("error",ex.getMessage()));
            return "redirect:/reviews";
        }
    }

    @PostMapping("/reviews/save")
    public String saveReview(Review reviewInform, RedirectAttributes rs){
        reviewService.save(reviewInform);
        rs.addFlashAttribute("message", new Message("success","The review ID " + reviewInform.getId() + " has been updated successfully."));
        return "redirect:/reviews";
    }
    
    @GetMapping("/reviews/delete/{id}")
    public String deleteReview(@PathVariable("id") Integer id, RedirectAttributes rs){
        try {
            reviewService.delete(id);
            rs.addFlashAttribute("message",new Message("success","The review ID " + id + " has been deleted."));
        } catch (ReviewNotFoundException ex){
            rs.addFlashAttribute("message",new Message("error",ex.getMessage()));
        }

        return "redirect:/reviews";
    }
}
