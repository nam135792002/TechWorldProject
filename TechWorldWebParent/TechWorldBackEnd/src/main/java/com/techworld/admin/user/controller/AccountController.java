package com.techworld.admin.user.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.techworld.admin.security.TechWorldUserDetails;
import com.techworld.admin.user.UserNotFoundException;
import com.techworld.admin.user.UserService;
import com.techworld.common.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Map;

@Controller
public class AccountController {
    @Autowired
    private UserService userService;

    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("/account")
    public String viewDetails(@AuthenticationPrincipal TechWorldUserDetails loggedUser, Model model){
        String email = loggedUser.getUsername();
        User user = userService.getByEmail(email);
        model.addAttribute("user", user);
        return "users/account_form";
    }

    @PostMapping("/account/update")
    public String saveDetails(User user, RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal TechWorldUserDetails loggedUser,
                              HttpSession httpSession,
                              @RequestParam("image") MultipartFile multipartFile) throws IOException, UserNotFoundException {
        if(!multipartFile.isEmpty()){
            if(user.getId() != null && userService.get(user.getId()).getPhotos() != null){
                userService.deleteImageInCloudinary(user);
            }
            try {
                Map r = this.cloudinary.uploader().upload(multipartFile.getBytes(),
                        ObjectUtils.asMap("resource_type","auto"));
                String img = (String) r.get("secure_url");
                user.setPhotos(img);
                User savedUser = userService.updateAccount(user);
            } catch (IOException e){
                e.printStackTrace();
            }
        } else{
            if (user.getId() == null || userService.get(user.getId()).getPhotos() == null){
                user.setPhotos(null);
            }
            User savedUser = userService.updateAccount(user);
        }

        loggedUser.setFirstname(user.getFirstName());
        loggedUser.setLastname(user.getLastName());
        loggedUser.setPhoto(user.getPhotos());
        httpSession.removeAttribute("fullname");
        httpSession.setAttribute("fullname",loggedUser.getFullname());
        httpSession.removeAttribute("photo");
        httpSession.setAttribute("photo", loggedUser.getPhoto());
        redirectAttributes.addFlashAttribute("message","Your account details have been updated.");
        return "redirect:/account";
    }
}
