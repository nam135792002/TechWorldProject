package com.techworld.admin.message.repository;

import com.techworld.common.entity.GroupMember;
import com.techworld.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberRepository extends JpaRepository<GroupMember,Integer> {

    public GroupMember findByUser(User user);
}
