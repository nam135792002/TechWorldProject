package com.techworld.admin.message.repository;

import com.techworld.common.entity.Group;
import com.techworld.common.entity.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserAndGroupRepository extends JpaRepository<Group, Integer> {

    @Query("select g from Group g inner join GroupMember m on g.id = m.group.id where m.user.id = ?1")
    public Group fetchAllUser(Integer userId);
}
