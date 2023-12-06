package com.techworld.contact;

import com.techworld.common.entity.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {

    @Autowired ContactService contactService;

    @GetMapping("/contact")
    public String viewContactPage(Model model){

        model.addAttribute("feed", new Feed());
        return "contact/contact";
    }

    @PostMapping("/contact/save")
    public String saveInfoContact(Feed feed, RedirectAttributes rs){
        contactService.save(feed);

        rs.addFlashAttribute("message","Phản hồi của bạn đã được lưu lại!");
        return "redirect:/contact";
    }

    @GetMapping("/about-us")
    public String viewAboutPage(){
        return "about/about_us";
    }

    @GetMapping("/policy-us")
    public String viewPolicyPage(){
        return "about/policy_us";
    }
}
