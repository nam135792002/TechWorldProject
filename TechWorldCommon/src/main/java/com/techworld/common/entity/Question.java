package com.techworld.common.entity;

import jakarta.persistence.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "question_content", nullable = false, length = 500)
    private String questionContent;

    @Column(name = "ask_time", nullable = false)
    private Date askTime;

    @Column(name = "answer_content", length = 500)
    private String answerContent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "answer_time")
    private Date answerTime;

    private boolean approvalStatus;
    private int votes;

    @Transient
    private boolean upvotedByCurrentCustomer;

    @Transient
    private boolean downvotedByCurrentCustomer;

    public Question() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public Date getAskTime() {
        return askTime;
    }

    public void setAskTime(Date askTime) {
        this.askTime = askTime;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }

    public boolean isApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(boolean approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Transient
    public String getUpdatedTimeOnForm(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(this.askTime);
    }

    @Transient
    public String getAnswerTimeQuestion(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(this.answerTime);
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
}
