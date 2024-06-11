package com.techworld.common.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "question_votes")
public class QuestionVote extends VoteBaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    public QuestionVote() {

    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
