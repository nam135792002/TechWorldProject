package com.techworld.review;

import com.techworld.common.entity.Customer;
import com.techworld.common.entity.OrderStatus;
import com.techworld.common.entity.Product;
import com.techworld.common.entity.Review;
import com.techworld.order.OrderDetailRepository;
import com.techworld.product.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Transactional
@Service
public class ReviewService {

    @Autowired private ReviewRepository reviewRepository;
    @Autowired private OrderDetailRepository orderDetailRepository;
    @Autowired private ProductRepository productRepository;

    public boolean didCustomerReviewProduct(Customer customer, Integer productId){
        long count = reviewRepository.countByCustomerAndProduct(customer.getId(), productId);
        return count > 0;
    }

    public boolean canCustomerReviewProduct(Customer customer, Integer productId){
        Long count = orderDetailRepository.countByProductAndCustomerAndAndOrderStatus(productId, customer.getId(), OrderStatus.DELIVERED);
        return count > 0;
    }

    public List<Review> listReviewByProduct(Product product){
        return reviewRepository.findByProduct(product);
    }

    public Review save(Review review){
        review.setReviewTime(new Date());
        Review savedReview = reviewRepository.save(review);
        Integer productId = savedReview.getProduct().getId();
        productRepository.updateReviewCountAndAverageRating(productId);
        return savedReview;
    }
}
