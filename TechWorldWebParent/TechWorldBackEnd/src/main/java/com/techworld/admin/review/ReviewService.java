package com.techworld.admin.review;

import com.techworld.admin.product.ProductRepository;
import com.techworld.admin.product.ProductService;
import com.techworld.common.entity.Review;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Transactional
@Service
public class ReviewService {

    @Autowired private ReviewRepository repository;
    @Autowired private ProductRepository productRepository;

    public List<Review> listByPage(){
        return repository.findAll();
    }

    public Review get(Integer id) throws ReviewNotFoundException {
        try {
            return repository.findById(id).get();
        }catch (NoSuchElementException ex){
            throw new ReviewNotFoundException("Could not find any reviews with ID "+ id);
        }
    }

    public void save(Review reviewInForm){
        Review reviewInDB = repository.findById(reviewInForm.getId()).get();
        reviewInDB.setHeadline(reviewInForm.getHeadline());
        reviewInDB.setComment(reviewInForm.getComment());

        repository.save(reviewInDB);
        productRepository.updateReviewCountAndAverageRating(reviewInDB.getProduct().getId());
    }

    public void delete(Integer id) throws ReviewNotFoundException {
        if (!repository.existsById(id)){
            throw new ReviewNotFoundException("Could not find any reviews with ID " + id);
        }

        repository.deleteById(id);
    }

    public long countReview(){
        return repository.count();
    }
}
