package com.techworld.admin.order;

import com.techworld.common.entity.Order;
import com.techworld.common.entity.OrderStatus;
import com.techworld.common.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    public Long countById(Integer id);

    @Query("select new com.techworld.common.entity.Order(o.id, o.orderTime, o.total) from Order o where  " +
            "o.orderTime between ?1 and ?2 order by o.orderTime asc")
    public List<Order> findByOrderTimeBetween(Date startTime, Date endTime);

    @Query("select sum(o.total) from Order o")
    public int sumIncome();

    @Query("select o from Order o where o.status = ?1")
    public List<Order> findOrderByStatus(OrderStatus orderStatus);

    @Query("select o from Order o where o.paymentMethod = ?1")
    public List<Order> findOrderByPayment(PaymentMethod paymentMethod);
}
