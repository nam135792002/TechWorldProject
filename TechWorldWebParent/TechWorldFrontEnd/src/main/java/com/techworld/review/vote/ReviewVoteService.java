package com.techworld.review.vote;

import com.techworld.common.entity.Customer;
import com.techworld.common.entity.Review;
import com.techworld.common.entity.ReviewVote;
import com.techworld.review.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ReviewVoteService {

    @Autowired private ReviewRepository reviewRepository;
    @Autowired private ReviewVoteRepository reviewVoteRepository;

    public VoteResult undoVote(ReviewVote vote, Integer reviewId, VoteType voteType){
        reviewVoteRepository.delete(vote);
        reviewRepository.updateVoteCount(reviewId);
        Integer voteCount = reviewRepository.getVoteCount(reviewId);

        return VoteResult.success("You have unvoted " + voteType + " that review.", voteCount);
    }

    public VoteResult doVote(Integer reviewId, Customer customer, VoteType voteType){
        Review review = null;

        try{
            review = reviewRepository.findById(reviewId).get();
        }catch (NoSuchElementException ex){
            return VoteResult.fail("The review ID " + reviewId + " no longer exists.");
        }

        ReviewVote reviewVote = reviewVoteRepository.findByReviewAndCustomer(reviewId, customer.getId());
        if (reviewVote != null){
            if(reviewVote.isUpvoted() && voteType.equals(VoteType.UP) || reviewVote.isDownvoted() && voteType.equals(VoteType.DOWN)){
                return undoVote(reviewVote, reviewId, voteType);
            } else if (reviewVote.isUpvoted() && voteType.equals(VoteType.DOWN)) {
                reviewVote.voteDown();
            } else if (reviewVote.isDownvoted() && voteType.equals(VoteType.UP)) {
                reviewVote.voteUp();
            }
        }else {
            reviewVote = new ReviewVote();
            reviewVote.setCustomer(customer);
            reviewVote.setReview(review);

            if(voteType.equals(VoteType.UP)){
                reviewVote.voteUp();
            }else {
                reviewVote.voteDown();
            }
        }

        reviewVoteRepository.save(reviewVote);
        reviewRepository.updateVoteCount(reviewId);
        Integer voteCount = reviewRepository.getVoteCount(reviewId);

        return VoteResult.success("You have successfully voted " + voteType + " that review.",voteCount);
    }

    public void markReviewsVotedForProductByCustomer(List<Review> listReviews, Integer productId, Integer customerId){
        List<ReviewVote> listVotes = reviewVoteRepository.findByProductAndCustomer(productId, customerId);

        for (ReviewVote vote : listVotes){
            Review review = vote.getReview();

            if(listReviews.contains(review)){
                int index = listReviews.indexOf(review);
                Review review1 = listReviews.get(index);

                if(vote.isUpvoted()){
                    review1.setUpvotedByCurrentCustomer(true);
                } else if ((vote.isDownvoted())) {
                    review1.setDownvotedByCurrentCustomer(true);
                }
            }
        }
    }
}
