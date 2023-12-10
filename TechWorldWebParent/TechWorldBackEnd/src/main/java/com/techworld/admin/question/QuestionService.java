package com.techworld.admin.question;

import com.techworld.admin.brand.BrandNotFoundException;
import com.techworld.common.entity.Question;
import com.techworld.common.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
public class QuestionService {
    @Autowired private QuestionRepository questionRepository;

    public List<Question> findAllBySortAskTime(){
        Sort sort = Sort.by("askTime").descending();

        return questionRepository.findAll(sort);
    }

    public void updateQuestionApprovalStatus(Integer id, boolean approval){
        questionRepository.updateApprovalStatus(id, approval);
    }

    public Question getQuestion(Integer questionId) throws QuestionNotFoundException {
        try{
            return questionRepository.findById(questionId).get();
        }catch (NoSuchElementException e){
            throw new QuestionNotFoundException("Could not find any brand with ID " + questionId);
        }
    }

    public void saveQuestion(User user, Question questionInForm){
        Question questionInDB = questionRepository.findById(questionInForm.getId()).get();

        if(questionInForm.getAnswerContent() != null && !Objects.equals(questionInForm.getAnswerContent(), "")){
            questionInDB.setAnswerContent(questionInForm.getAnswerContent());
            questionInDB.setUser(user);
            questionInDB.setAnswerTime(new Date());
        }
        questionInDB.setQuestionContent(questionInForm.getQuestionContent());
        questionInDB.setApprovalStatus(questionInForm.isApprovalStatus());

        questionRepository.save(questionInDB);
    }
}
