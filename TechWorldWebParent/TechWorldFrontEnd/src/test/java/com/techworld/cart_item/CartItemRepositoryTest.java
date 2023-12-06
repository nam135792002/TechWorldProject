package com.techworld.cart_item;

import com.techworld.cart.CartItemRepository;
import com.techworld.common.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class CartItemRepositoryTest {
    @Autowired private CartItemRepository cartItemRepository;

    @Test
    public void testCountCartItemByCustomer(){
        Customer customer = new Customer(1);

        long count = cartItemRepository.countCartItemByCustomer(customer);
        System.out.println(count);
    }
}
