package com.techworld.order;

import com.techworld.common.entity.OrderStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class OrderDetailRepositoryTest {

    @Autowired private OrderDetailRepository orderDetailRepository;

    @Test
    public void testCountByProductAndCustomerAndOrderStatus(){
        Integer productId = 61;
        Integer customerId = 13;

        Long count = orderDetailRepository.countByProductAndCustomerAndAndOrderStatus(productId, customerId, OrderStatus.DELIVERED);
        Assertions.assertThat(count).isGreaterThan(0);
    }
}
