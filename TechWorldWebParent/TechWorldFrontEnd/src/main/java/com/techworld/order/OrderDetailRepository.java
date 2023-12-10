package com.techworld.order;

import com.techworld.common.entity.OrderDetail;
import com.techworld.common.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    @Query("select count(d) from OrderDetail d join OrderTrack t on d.order.id = t.order.id " +
            "where d.product.id = ?1 and d.order.customer.id = ?2 and t.status = ?3 and d.order.id = ?4")
    public Long countByProductAndCustomerAndAndOrderStatus(Integer productId, Integer customerId, OrderStatus status, Integer orderId);

}
