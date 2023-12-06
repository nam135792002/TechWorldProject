package com.techworld.order;

import com.techworld.common.entity.Customer;
import com.techworld.common.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("select o from Order o where o.customer.id = ?1")
    public List<Order> findAll(Integer customerId);

    public Order findByIdAndCustomer(Integer id, Customer customer);
}
