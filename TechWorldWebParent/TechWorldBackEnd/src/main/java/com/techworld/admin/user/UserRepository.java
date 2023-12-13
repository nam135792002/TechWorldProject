package com.techworld.admin.user;

import com.techworld.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.email = :email")
    public User getUserByEmail(@Param("email") String email);

    public Long countById(Integer id);

    @Query("select u from User u where u.id != ?1")
    public List<User> listUserWithoutUserId(Integer userId);

    @Query("update User u set u.enabled = ?2 where u.id = ?1")
    @Modifying
    public void updateEnabledStatus(Integer id, boolean enabled);

}
