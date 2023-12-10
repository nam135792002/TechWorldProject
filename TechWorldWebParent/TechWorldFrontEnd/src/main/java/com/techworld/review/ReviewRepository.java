package com.techworld.review;

import com.techworld.common.entity.Order;
import com.techworld.common.entity.Product;
import com.techworld.common.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("select r from Review r where r.customer.id = ?1")
    public List<Review> findByCustomer(Integer customerId);

    @Query("select r from Review r where  r.customer.id = ?1 and r.id = ?2")
    public Review findByCustomerAndId(Integer customerId, Integer reviewId);

    public List<Review> findByProduct(Product product);

    @Query("select count(r.id) from Review r where r.customer.id = ?1 and r.product.id = ?2 and r.order.id = ?3")
    public Long countByCustomerAndProduct(Integer customerId, Integer productId, Integer orderId);

    @Query("update Review r set r.votes = coalesce(cast((select sum(v.votes) from ReviewVote v " +
            "where v.review.id = ?1) as int),0) where r.id = ?1")
    @Modifying
    public void updateVoteCount(Integer reviewId);

    @Query("select r.votes from Review r where r.id = ?1")
    public Integer getVoteCount(Integer reviewId);
}
