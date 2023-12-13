package com.techworld.admin.message.repository;

import com.techworld.common.entity.MessageMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageMemberRepository extends JpaRepository<MessageMember, Integer> {

    @Query("select m from MessageMember m where (m.fromUser.id = ?1 and m.toUser.id = ?2) or (m.fromUser.id = ?2 and m.toUser.id = ?1) order by m.createdTime asc")
    public List<MessageMember> listAllMessageMember(Integer from, Integer to);

}
