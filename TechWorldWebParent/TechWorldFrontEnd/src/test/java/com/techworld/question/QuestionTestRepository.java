package com.techworld.question;

import com.techworld.common.entity.Customer;
import com.techworld.common.entity.Product;
import com.techworld.common.entity.Question;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class QuestionTestRepository {
    @Autowired private QuestionRepository questionRepository;

    @Test
    public void testSaveQuestion01(){
        Integer productId = 51;
        Integer customerId = 1;

        Question question = new Question();
        question.setProduct(new Product(productId));
        question.setCustomer(new Customer(customerId));

        String questionContent = "Không biết tuô thọ của con củ sạc này dùng được lâu không ạ?";
        question.setQuestionContent(questionContent);
        question.setAskTime(new Date());

        Question savedQuestion = questionRepository.save(question);
        Assertions.assertThat(savedQuestion.getId()).isGreaterThan(0);
    }

    @Test
    public void testSaveQuestion02(){
        Integer productId = 9;
        Integer customerId = 1;

        Question question = new Question();
        question.setProduct(new Product(productId));
        question.setCustomer(new Customer(customerId));

        String questionContent = "Thẻ nhớ này có dùng cho máy nghe nhạc mp3 được không shop";
        question.setQuestionContent(questionContent);
        question.setAskTime(new Date());

        Question savedQuestion = questionRepository.save(question);
        Assertions.assertThat(savedQuestion.getId()).isGreaterThan(0);
    }

    @Test
    public void testSaveQuestion03(){
        Integer productId = 69;
        Integer customerId = 1;

        Question question = new Question();
        question.setProduct(new Product(productId));
        question.setCustomer(new Customer(customerId));

        String questionContent = "Thẻ nhớ này có dùng cho máy nghe nhạc mp3 được không shop";
        question.setQuestionContent(questionContent);
        question.setAskTime(new Date());

        Question savedQuestion = questionRepository.save(question);
        Assertions.assertThat(savedQuestion.getId()).isGreaterThan(0);
    }
}
