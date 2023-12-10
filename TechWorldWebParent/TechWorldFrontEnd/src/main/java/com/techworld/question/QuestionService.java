package com.techworld.question;

import com.techworld.common.entity.Customer;
import com.techworld.common.entity.Product;
import com.techworld.common.entity.Question;
import com.techworld.product.ProductNotFoundException;
import com.techworld.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class QuestionService {

    @Autowired private QuestionRepository questionRepository;

    public List<Question> listQuestionByProduct(Product product){
        return questionRepository.findByProduct(product);
    }

    public void saveQuestion(Question question, Customer customer, Product product) throws ProductNotFoundException {
        question.setCustomer(customer);
        question.setProduct(product);
        question.setAskTime(new Date());

        questionRepository.save(question);
    }
}
