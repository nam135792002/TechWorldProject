package com.techworld.common.entity;

import jakarta.persistence.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "reviews")
public class Review{

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 128, nullable = false)
    private String headline;

    @Column(length = 300, nullable = false)
    private String comment;

    private int rating;

    private int votes;

    @Column(nullable = false)
    private Date reviewTime;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Transient
    private boolean upvotedByCurrentCustomer;

    @Transient
    private boolean downvotedByCurrentCustomer;

    public Review() {

    }

    public Review(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Review{" +
                "headline='" + headline + '\'' +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                ", reviewTime=" + reviewTime +
                ", product=" + product.getShortName() +
                ", customer=" + customer.getFullName() +
                '}';
    }

    public String getUpdatedTimeOnForm() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(this.reviewTime);
    }

    public boolean isUpvotedByCurrentCustomer() {
        return upvotedByCurrentCustomer;
    }

    public void setUpvotedByCurrentCustomer(boolean upvotedByCurrentCustomer) {
        this.upvotedByCurrentCustomer = upvotedByCurrentCustomer;
    }

    public boolean isDownvotedByCurrentCustomer() {
        return downvotedByCurrentCustomer;
    }

    public void setDownvotedByCurrentCustomer(boolean downvotedByCurrentCustomer) {
        this.downvotedByCurrentCustomer = downvotedByCurrentCustomer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review review)) return false;
        return Objects.equals(getId(), review.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
