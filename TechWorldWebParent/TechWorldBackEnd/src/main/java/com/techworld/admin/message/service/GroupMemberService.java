package com.techworld.admin.message.service;

import com.techworld.admin.message.repository.GroupMemberRepository;
import com.techworld.common.entity.GroupMember;
import com.techworld.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupMemberService {

    @Autowired private GroupMemberRepository groupMemberRepository;

    public void save(GroupMember groupMember){
        groupMemberRepository.save(groupMember);
    }

    public boolean checkUserInDatabase(User user){
        GroupMember groupMember = groupMemberRepository.findByUser(user);
        return groupMember == null;
    }
}
