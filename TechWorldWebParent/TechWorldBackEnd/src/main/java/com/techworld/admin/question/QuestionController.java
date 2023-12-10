package com.techworld.admin.question;

import com.techworld.admin.Message;
import com.techworld.admin.Utility;
import com.techworld.admin.user.UserNotFoundException;
import com.techworld.admin.user.UserService;
import com.techworld.common.entity.Customer;
import com.techworld.common.entity.Question;
import com.techworld.common.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired private QuestionService questionService;
    @Autowired private UserService userService;

    @GetMapping("/questions")
    public String listByPage(Model model){
        List<Question> listQuestions = questionService.findAllBySortAskTime();

        model.addAttribute("listQuestions", listQuestions);
        return "questions/questions";
    }

    @GetMapping("/questions/detail/{id}")
    public String detailQuestionByPage(@PathVariable("id") Integer id, Model model, RedirectAttributes rs){
        try{
            Question question = questionService.getQuestion(id);

            model.addAttribute("question", question);
        }catch (QuestionNotFoundException ex){
            rs.addFlashAttribute("message", new Message("error", ex.getMessage()));
        }
        return "questions/question_detail_modal";
    }

    @GetMapping("/questions/edit/{id}")
    public String editQuestionByPage(@PathVariable("id") Integer id, Model model, RedirectAttributes rs){
        try{
            Question question = questionService.getQuestion(id);

            model.addAttribute("question", question);
        }catch (QuestionNotFoundException ex){
            rs.addFlashAttribute("message", new Message("error", ex.getMessage()));
        }
        return "questions/question_edit_modal";
    }

    @PostMapping("/questions/save")
    public String saveQuestion(Question question, RedirectAttributes rs, HttpServletRequest request) throws UserNotFoundException {
        User user = getAuthenticateUser(request);

        questionService.saveQuestion(user, question);
        rs.addFlashAttribute("message", new Message("success", "This question was updated successfully"));
        return "redirect:/questions";
    }

    private User getAuthenticateUser(HttpServletRequest request) throws UserNotFoundException {
        String email = Utility.getEmailOfAuthenticatedUser(request);
        return userService.getByEmail(email);
    }
}
