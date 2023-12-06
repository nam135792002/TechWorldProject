package com.techworld.admin.order;

import com.techworld.common.entity.Order;
import com.techworld.common.entity.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.text.DecimalFormat;
import java.util.List;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class OrderRepositoryTests {
    @Autowired
    private OrderRepository repository;

    @Test
    public void testOrder() {
        List<Order> listOrderByCanceled = repository.findOrderByStatus(OrderStatus.CANCELED);
        List<Order> listOrderByCompleted = repository.findOrderByStatus(OrderStatus.DELIVERED);
        long listOrder = repository.count();

        double countOrderByCanceled = listOrderByCanceled.size() * 100.0 / listOrder;
        double countOrderByCompleted = listOrderByCompleted.size() * 100.0 / listOrder;
        double countOrderByInProgress = (listOrder - listOrderByCanceled.size() - listOrderByCompleted.size()) * 100.0 / listOrder;

        // Format percentages to two decimal places
        DecimalFormat df = new DecimalFormat("0.00");
        String formattedCountOrderByCanceled = df.format(countOrderByCanceled);
        String formattedCountOrderByCompleted = df.format(countOrderByCompleted);
        String formattedCountOrderByInProgress = df.format(countOrderByInProgress);

        System.out.println(formattedCountOrderByCanceled);
        System.out.println(formattedCountOrderByCompleted);
        System.out.println(formattedCountOrderByInProgress);
    }

}
