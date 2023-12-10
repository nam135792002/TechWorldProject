package com.techworld.question.vote;

import com.techworld.common.entity.QuestionVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionVoteRepository extends JpaRepository<QuestionVote, Integer> {

    @Query("select q from QuestionVote q where q.question.id = ?1 and q.customer.id = ?2")
    public QuestionVote findByQuestionAndCustomer(Integer question, Integer customerId);

    @Query("select q from QuestionVote q where q.question.product.id = ?1 and q.customer.id = ?2")
    public List<QuestionVote> findByProductAndCustomer(Integer productId, Integer customerId);
}
