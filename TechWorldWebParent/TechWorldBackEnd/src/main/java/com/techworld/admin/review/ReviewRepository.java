package com.techworld.admin.review;

import com.techworld.common.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ReviewRepository extends JpaRepository<Review, Integer> {

}
