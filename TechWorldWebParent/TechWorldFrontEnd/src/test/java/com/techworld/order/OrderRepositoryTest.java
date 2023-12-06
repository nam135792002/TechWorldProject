package com.techworld.order;

import com.techworld.common.entity.Order;
import com.techworld.common.entity.OrderStatus;
import com.techworld.common.entity.OrderTrack;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class OrderRepositoryTest {
    @Autowired OrderRepository orderRepository;

    @Test
    public void testUpdateOrderTrack(){
        List<Order> listOrders = orderRepository.findAll();
        for (Order order : listOrders){
            OrderTrack orderTrack = new OrderTrack(OrderStatus.NEW.defaultDescription(), order.getOrderTime(), OrderStatus.NEW, order);
            order.addTrack(orderTrack);

            orderRepository.save(order);
        }
    }
}
