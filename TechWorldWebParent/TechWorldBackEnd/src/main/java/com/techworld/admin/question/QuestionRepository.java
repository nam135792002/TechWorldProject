package com.techworld.admin.question;

import com.techworld.common.entity.Question;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@NonNullApi
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    public List<Question> findAll(Sort sort);

    @Query("update Question  q set q.approvalStatus = ?2 where q.id = ?1")
    @Modifying
    public void updateApprovalStatus(Integer id, boolean enabled);
}
