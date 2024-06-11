package com.techworld.common.entity;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class VoteBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    protected static final int VOTE_UP_POINT = 1;
    protected static final int VOTE_DOWN_POINT = -1;
    protected int votes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void voteUp(){
        this.votes = VOTE_UP_POINT;
    }

    public void voteDown(){
        this.votes = VOTE_DOWN_POINT;
    }

    @Transient
    public boolean isUpvoted(){
        return this.votes == VOTE_UP_POINT;
    }

    @Transient
    public boolean isDownvoted(){
        return  this.votes == VOTE_DOWN_POINT;
    }
}
