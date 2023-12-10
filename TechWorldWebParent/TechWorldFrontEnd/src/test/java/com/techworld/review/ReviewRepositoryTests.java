package com.techworld.review;

import com.techworld.common.entity.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ReviewRepositoryTests {

    @Autowired private ReviewRepository repository;

    @Test
    public void testCountByCustomerAndProduct(){
        Integer customerId = 13;
        Integer productId = 61;
        Integer orderId = 12;

        Long count = repository.countByCustomerAndProduct(customerId, productId, orderId);

        Assertions.assertThat(count).isEqualTo(1);
    }

    @Test
    public void testUpdateVoteCount(){
        Integer reviewId = 2;
        repository.updateVoteCount(reviewId);
        Review review = repository.findById(reviewId).get();

        Assertions.assertThat(review.getVotes()).isEqualTo(1);
    }

    @Test
    public void testGetVoteCount(){
        Integer reviewId = 2;
        Integer voteCount = repository.getVoteCount(reviewId);
        Assertions.assertThat(voteCount).isEqualTo(1);
    }
}
