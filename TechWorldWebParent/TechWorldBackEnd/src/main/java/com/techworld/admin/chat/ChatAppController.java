package com.techworld.admin.chat;

import com.techworld.admin.Utility;
import com.techworld.admin.message.dto.MessageGroupDTO;
import com.techworld.admin.message.service.UserAndGroupService;
import com.techworld.admin.user.UserNotFoundException;
import com.techworld.admin.user.UserService;
import com.techworld.common.entity.Group;
import com.techworld.common.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ChatAppController {

    @Autowired private UserAndGroupService userAndGroupService;
    @Autowired private UserService userService;

    @GetMapping("/chat")
    public String chatAppPage(HttpServletRequest request, Model model) throws UserNotFoundException {
        User user = getAuthenticateUser(request);
        Group group = userAndGroupService.findGroupByUserId(user.getId());
        List<User> listUser = userService.listNotUser(user.getId());

        listUser.forEach(System.out::println);

        model.addAttribute("group", group);
        model.addAttribute("listUser", listUser);
        return "chat/chat_app";
    }

    private User getAuthenticateUser(HttpServletRequest request) throws UserNotFoundException {
        String email = Utility.getEmailOfAuthenticatedUser(request);
        return userService.getByEmail(email);
    }
}
