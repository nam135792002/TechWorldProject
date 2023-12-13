package com.techworld.admin.message.service;

import com.techworld.admin.message.repository.MessageMemberRepository;
import com.techworld.admin.user.UserNotFoundException;
import com.techworld.admin.user.UserService;
import com.techworld.common.entity.MessageMember;
import com.techworld.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageMemberService {
    @Autowired private MessageMemberRepository repository;
    @Autowired private UserService userService;

    public List<MessageMember> listAllMessageMemberShip(User from, Integer toId) throws UserNotFoundException {
        return repository.listAllMessageMember(from.getId(), toId);
    }

    public MessageMember saveMessageMember(User from, User to, String message){
        MessageMember messageMember = new MessageMember();
        messageMember.setMessage(message);
        messageMember.setFromUser(from);
        messageMember.setToUser(to);
        messageMember.setCreatedTime(new Date());

        return repository.save(messageMember);
    }
}
