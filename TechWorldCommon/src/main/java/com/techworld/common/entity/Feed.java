package com.techworld.common.entity;

import jakarta.persistence.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "feed_backs")
public class Feed extends PersonalBaseEntity{

    @Column(name = "full_name", nullable = false, length = 45)
    private String fullName;

    @Column(nullable = false, name = "message", columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    private FeedBackStatus feedBackStatus;

    private Date sentTime;

    public Feed() {

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FeedBackStatus getFeedBackStatus() {
        return feedBackStatus;
    }

    public void setFeedBackStatus(FeedBackStatus feedBackStatus) {
        this.feedBackStatus = feedBackStatus;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    @Transient
    public String getUpdatedTimeOnForm(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd' | 'hh:mm:ss");
        return dateFormat.format(this.sentTime);
    }
}
