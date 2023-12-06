package com.techworld.review;

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

        Long count = repository.countByCustomerAndProduct(customerId, productId);

        Assertions.assertThat(count).isEqualTo(1);
    }
}
