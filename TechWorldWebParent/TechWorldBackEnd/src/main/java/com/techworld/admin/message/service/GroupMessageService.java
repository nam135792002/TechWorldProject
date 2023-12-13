package com.techworld.admin.message.service;

import com.techworld.admin.message.dto.MessageDTO;
import com.techworld.admin.message.repository.GroupMessageRepository;
import com.techworld.common.entity.Group;
import com.techworld.common.entity.GroupMessage;
import com.techworld.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GroupMessageService {
    @Autowired private GroupMessageRepository groupMessageRepository;

    public GroupMessage saveGroupMessage(MessageDTO messageDTO, User user){
        GroupMessage groupMessage = new GroupMessage();

        groupMessage.setCreatedTime(new Date());
        groupMessage.setMessage(messageDTO.getMessage());
        groupMessage.setGroup(new Group(1));
        groupMessage.setUser(user);

        return groupMessageRepository.save(groupMessage);
    }

    public List<GroupMessage> listGroupMessage(){
        return groupMessageRepository.findAllGroupMessage();
    }
}
