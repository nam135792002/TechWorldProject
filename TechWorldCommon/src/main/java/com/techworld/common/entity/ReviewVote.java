package com.techworld.common.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "review_votes")
public class ReviewVote extends VoteBaseEntity{

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    public ReviewVote() {

    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "ReviewVote{" +
                "votes=" + this.votes +
                ", customer=" + this.review.getCustomer().getFullName() +
                ", review=" + review.getComment() +
                '}';
    }
}
