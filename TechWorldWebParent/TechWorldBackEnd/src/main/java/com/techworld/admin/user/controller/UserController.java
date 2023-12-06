package com.techworld.admin.user.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.techworld.admin.Message;
import com.techworld.admin.user.UserNotFoundException;
import com.techworld.admin.user.UserService;
import com.techworld.admin.user.export.UserCsvExporter;
import com.techworld.admin.user.export.UserExcelExporter;
import com.techworld.admin.user.export.UserPdfExporter;
import com.techworld.common.entity.Role;
import com.techworld.common.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("/users")
    public String listAll(Model model){
        List<User> listUsers = userService.listAll();
        model.addAttribute("listUsers",listUsers);
        return "users/users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model){
        List<Role> listRoles = userService.listRoles();

        User user = new User();
        user.setEnabled(true);
        model.addAttribute("user",user);
        model.addAttribute("listRoles",listRoles);
        model.addAttribute("pageTitle","Create New User");
        return "users/user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes,
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
                User savedUser = userService.save(user);
            } catch (IOException e){
                e.printStackTrace();
            }
        } else{
            if (user.getId() == null || userService.get(user.getId()).getPhotos() == null){
                user.setPhotos(null);
            }
            User savedUser = userService.save(user);
        }
        redirectAttributes.addFlashAttribute("message", new Message("success", "The user has been saved successfully."));

        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes, Model model){
        try {
            User user = userService.get(id);
            List<Role> listRoles = userService.listRoles();
            model.addAttribute("user",user);
            model.addAttribute("pageTitle","Edit User (ID: " + id + ")");
            model.addAttribute("listRoles",listRoles);
            return "users/user_form";
        }catch (UserNotFoundException ex){
            redirectAttributes.addFlashAttribute("message",new Message("error", ex.getMessage()));
            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes, Model model){
        try {
            User user = userService.get(id);
            if(user.getPhotos() != null){
                userService.deleteImageInCloudinary(user);
            }
            userService.delete(id);
            redirectAttributes.addFlashAttribute("message",new Message("success","The user ID " + id + " has been deleted successfully"));
        }catch (UserNotFoundException ex){
            redirectAttributes.addFlashAttribute("message",new Message("error", ex.getMessage()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/users";
    }

    @GetMapping("/users/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<User> listUsers = userService.listAll();
        UserCsvExporter exporter = new UserCsvExporter();
        exporter.export(listUsers,response);
    }

    @GetMapping("/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<User> listUsers = userService.listAll();
        UserExcelExporter exporter = new UserExcelExporter();
        exporter.export(listUsers,response);
    }

    @GetMapping("/users/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        List<User> listUsers = userService.listAll();
        UserPdfExporter exporter = new UserPdfExporter();
        exporter.export(listUsers,response);
    }
}