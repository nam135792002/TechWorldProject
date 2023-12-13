package com.techworld.admin.message.controller;

import com.techworld.admin.Utility;
import com.techworld.admin.message.dto.GroupMessageDTO;
import com.techworld.admin.message.dto.MessageGroupDTO;
import com.techworld.admin.message.dto.MessageMemberDTO;
import com.techworld.admin.message.service.GroupMessageService;
import com.techworld.admin.message.service.MessageMemberService;
import com.techworld.admin.user.UserNotFoundException;
import com.techworld.admin.user.UserService;
import com.techworld.common.entity.GroupMessage;
import com.techworld.common.entity.MessageMember;
import com.techworld.common.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class ChatController {

    @Autowired private GroupMessageService groupMessageService;
    @Autowired private UserService userService;
    @Autowired private MessageMemberService messageMemberService;

    @MessageMapping("/chat/group")
    @SendTo("/topic/messages/group")
    public GroupMessageDTO sendMessageToGroup(@Payload MessageGroupDTO message) throws UserNotFoundException {
        User userInDB = userService.getByEmail(message.getEmail());
        GroupMessage groupMessage = groupMessageService.saveGroupMessage(message,userInDB);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(groupMessage.getCreatedTime());

        DateFormat dateFormat1 = new SimpleDateFormat("HH:mm a");
        String date1 = dateFormat1.format(groupMessage.getCreatedTime());

        return new GroupMessageDTO(date, groupMessage.getUser().getEmail(), groupMessage.getMessage(),
                groupMessage.getUser().getFullName(), groupMessage.getUser().getPhotos(), date1);
    }

    @GetMapping("/listmessage/group")
    public List<GroupMessageDTO> listAllGroupMessage(){
        List<GroupMessage> listGroupMessage = groupMessageService.listGroupMessage();
        List<GroupMessageDTO> groupMessageDTOS = new ArrayList<>();
        for (GroupMessage groupMessage : listGroupMessage){
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = dateFormat.format(groupMessage.getCreatedTime());

            DateFormat dateFormat1 = new SimpleDateFormat("HH:mm a");
            String date1 = dateFormat1.format(groupMessage.getCreatedTime());

            GroupMessageDTO messageDTO = new GroupMessageDTO(date, groupMessage.getUser().getEmail(), groupMessage.getMessage(),
                    groupMessage.getUser().getFullName(), groupMessage.getUser().getPhotos(), date1);
            groupMessageDTOS.add(messageDTO);
        }

        return groupMessageDTOS;
    }

    @GetMapping("/listmessage/{email}/{id}")
    public List<MessageMemberDTO> listAllMessageBetweenTwoUer(@PathVariable(name = "email") String email,
                                                              @PathVariable(name = "id") Integer id, HttpServletRequest request) throws UserNotFoundException {
        User from = getAuthenticateUser(request);
        List<MessageMember> listMessageMember = messageMemberService.listAllMessageMemberShip(from, id);
        List<MessageMemberDTO> messageMemberDTOS = new ArrayList<>();

        for (MessageMember messageMember : listMessageMember){
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = dateFormat.format(messageMember.getCreatedTime());

            DateFormat dateFormat1 = new SimpleDateFormat("HH:mm a");
            String date1 = dateFormat1.format(messageMember.getCreatedTime());

            MessageMemberDTO dto = new MessageMemberDTO(date, messageMember.getMessage(), messageMember.getFromUser().getEmail(),
                    messageMember.getFromUser().getFullName(), messageMember.getFromUser().getPhotos(), messageMember.getToUser().getEmail(),
                    messageMember.getToUser().getPhotos(), messageMember.getToUser().getFullName(), date1, messageMember.getToUser().getId(), messageMember.getFromUser().getId());

            messageMemberDTOS.add(dto);
        }

        return messageMemberDTOS;
    }

    @MessageMapping("/chat/{to}")
    @SendTo("/topic/messages/chat")
    public MessageMemberDTO sendMessageToUser(@Payload MessageGroupDTO message, @DestinationVariable Integer to) throws UserNotFoundException {
        User fromUser = userService.getByEmail(message.getEmail());
        User toUser = userService.get(to);

        MessageMember messageMember = messageMemberService.saveMessageMember(fromUser, toUser, message.getMessage());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(messageMember.getCreatedTime());

        DateFormat dateFormat1 = new SimpleDateFormat("HH:mm a");
        String date1 = dateFormat1.format(messageMember.getCreatedTime());

        return new MessageMemberDTO(date, messageMember.getMessage(), messageMember.getFromUser().getEmail(),
                messageMember.getFromUser().getFullName(), messageMember.getFromUser().getPhotos(), messageMember.getToUser().getEmail(),
                messageMember.getToUser().getPhotos(), messageMember.getToUser().getFullName(), date1, messageMember.getToUser().getId(), messageMember.getFromUser().getId());
    }

    private User getAuthenticateUser(HttpServletRequest request) throws UserNotFoundException {
        String email = Utility.getEmailOfAuthenticatedUser(request);
        return userService.getByEmail(email);
    }
}
