package com.techworld.question;

import com.techworld.common.entity.Product;
import com.techworld.common.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    public List<Question> findByProduct(Product product);

    @Query("update Question q set q.votes = coalesce(cast((select sum(v.votes) from QuestionVote v " +
            "where v.question.id = ?1) as int),0) where q.id = ?1")
    @Modifying
    public void updateVoteCount(Integer questionId);

    @Query("select q.votes from Question q where q.id = ?1")
    public Integer getVoteCount(Integer questionId);
}
