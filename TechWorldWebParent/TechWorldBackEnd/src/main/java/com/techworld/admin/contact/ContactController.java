package com.techworld.admin.contact;

import com.techworld.admin.Message;
import com.techworld.admin.Utility;
import com.techworld.admin.setting.EmailSettingBag;
import com.techworld.admin.setting.SettingService;
import com.techworld.common.entity.Feed;
import com.techworld.common.entity.FeedBackStatus;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class ContactController {

    @Autowired private ContactService contactService;
    @Autowired private SettingService settingService;

    @GetMapping("/contact")
    public String viewContactPage(Model model){
        List<Feed> listFeeds = contactService.listAll();

        model.addAttribute("listFeeds", listFeeds);
        return "contact/contact";
    }

    @GetMapping("/contact/detail/{contactId}")
    public String viewContactDetail(@PathVariable("contactId") Integer contactId, RedirectAttributes rs, Model model){
        try{
            Feed feed = contactService.get(contactId);

            model.addAttribute("feed", feed);
            return "contact/contact_detail_modal";
        }catch (FeedNotFoundException e){
            rs.addFlashAttribute("message", new Message("error", e.getMessage()));
            return "redirect:/contact";
        }
    }

    @PostMapping("/contact/send_email")
    public String sendEmailFeedback(RedirectAttributes rs, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException, FeedNotFoundException {
        String receiver = request.getParameter("receiver");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");
        Integer id = Integer.valueOf(request.getParameter("idContact"));

        Feed feed = contactService.get(id);
        feed.setFeedBackStatus(FeedBackStatus.RESPONDED);
        contactService.savedFeed(feed);

        EmailSettingBag emailSettingBags = settingService.getEmailSettings();
        JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettingBags);
        mailSender.setDefaultEncoding("utf-8");

        MimeMessage messageEmail = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(messageEmail);

        helper.setFrom(emailSettingBags.getForm(), emailSettingBags.getSenderName());
        helper.setTo(receiver);
        helper.setSubject(subject);
        helper.setText(message,true);
        mailSender.send(messageEmail);

        rs.addFlashAttribute("message", new Message("success","Send email success"));

        return "redirect:/contact";
    }
}
