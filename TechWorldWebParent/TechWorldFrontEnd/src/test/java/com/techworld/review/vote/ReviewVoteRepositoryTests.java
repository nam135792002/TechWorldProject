package com.techworld.review.vote;

import com.techworld.common.entity.Customer;
import com.techworld.common.entity.Review;
import com.techworld.common.entity.ReviewVote;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ReviewVoteRepositoryTests {

    @Autowired private ReviewVoteRepository repository;

    @Test
    public void testSaveVote(){
        Integer customerId = 1;
        Integer reviewId = 2;

        ReviewVote reviewVote = new ReviewVote();
        reviewVote.setCustomer(new Customer(customerId));
        reviewVote.setReview(new Review(reviewId));
        reviewVote.setVotes(1);

        ReviewVote savedVote = repository.save(reviewVote);
        Assertions.assertThat(savedVote.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindByReviewAndCustomer(){
        Integer customerId = 1;
        Integer reviewId = 1;

        ReviewVote reviewVote = repository.findByReviewAndCustomer(reviewId, customerId);
        Assertions.assertThat(reviewVote).isNotNull();

        System.out.println(reviewVote);
    }

    @Test
    public void testFindByProductAndCustomer(){
        Integer customerId = 1;
        Integer productId = 9;

        List<ReviewVote> listVotes = repository.findByProductAndCustomer(productId, customerId);
        Assertions.assertThat(listVotes.size()).isGreaterThan(0);

        listVotes.forEach(System.out::println);
    }
}
