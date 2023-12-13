package com.techworld.admin.message.repository;

import com.techworld.common.entity.GroupMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupMessageRepository extends JpaRepository<GroupMessage, Integer> {

    @Query("select m from GroupMessage m order by m.createdTime asc")
    public List<GroupMessage> findAllGroupMessage();
}
