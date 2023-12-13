package com.techworld.admin.message.service;

import com.techworld.admin.message.repository.UserAndGroupRepository;
import com.techworld.common.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAndGroupService {
    @Autowired private UserAndGroupRepository userAndGroupRepository;

    public Group findGroupByUserId(Integer userId){
        return userAndGroupRepository.fetchAllUser(userId);
    }


}
