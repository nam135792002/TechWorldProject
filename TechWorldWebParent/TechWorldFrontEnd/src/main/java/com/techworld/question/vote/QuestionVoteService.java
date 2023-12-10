package com.techworld.question.vote;

import com.techworld.common.entity.*;
import com.techworld.question.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class QuestionVoteService {

    @Autowired private QuestionRepository questionRepository;
    @Autowired private QuestionVoteRepository questionVoteRepository;

    public VoteResult undoVote(QuestionVote vote, Integer questionId, VoteType voteType){
        questionVoteRepository.delete(vote);
        questionRepository.updateVoteCount(questionId);
        Integer voteCount = questionRepository.getVoteCount(questionId);

        return VoteResult.success("You have unvoted " + voteType + " that question.", voteCount);
    }

    public VoteResult doVote(Integer questionId, Customer customer, VoteType voteType){
        Question question = null;

        try{
            question = questionRepository.findById(questionId).get();
        }catch (NoSuchElementException ex){
            return VoteResult.fail("The question ID " + questionId + " no longer exists.");
        }

        QuestionVote questionVote = questionVoteRepository.findByQuestionAndCustomer(questionId, customer.getId());
        if (questionVote != null){
            if(questionVote.isUpvoted() && voteType.equals(VoteType.UP) || questionVote.isDownvoted() && voteType.equals(VoteType.DOWN)){
                return undoVote(questionVote, questionId, voteType);
            } else if (questionVote.isUpvoted() && voteType.equals(VoteType.DOWN)) {
                questionVote.voteDown();
            } else if (questionVote.isDownvoted() && voteType.equals(VoteType.UP)) {
                questionVote.voteUp();
            }
        }else {
            questionVote = new QuestionVote();
            questionVote.setCustomer(customer);
            questionVote.setQuestion(question);

            if(voteType.equals(VoteType.UP)){
                questionVote.voteUp();
            }else {
                questionVote.voteDown();
            }
        }

        questionVoteRepository.save(questionVote);
        questionRepository.updateVoteCount(questionId);
        Integer voteCount = questionRepository.getVoteCount(questionId);

        return VoteResult.success("You have successfully voted " + voteType + " that question.",voteCount);
    }

    public void markQuestionsVotedForProductByCustomer(List<Question> listQuestions, Integer productId, Integer customerId){
        List<QuestionVote> listVotes = questionVoteRepository.findByProductAndCustomer(productId, customerId);

        for (QuestionVote vote : listVotes){
            Question question = vote.getQuestion();

            if(listQuestions.contains(question)){
                int index = listQuestions.indexOf(question);
                Question question1 = listQuestions.get(index);

                if(vote.isUpvoted()){
                    question1.setUpvotedByCurrentCustomer(true);
                } else if ((vote.isDownvoted())) {
                    question1.setDownvotedByCurrentCustomer(true);
                }
            }
        }
    }
}
