package com.techworld.admin.message.dto;

public class MessageMemberDTO {
    private String date;
    private String message;
    private String emailFrom;
    private String fullNameTo;
    private String avatarTo;
    private String emailTo;
    private String avartar;
    private String fullName;
    private String date1;
    private Integer userIdTo;
    private Integer userIdFrom;

    public MessageMemberDTO(String date, String message, String emailFrom, String fullNameTo, String avatarTo, String emailTo, String avartar, String fullName, String date1, Integer userIdTo, Integer userIdFrom) {
        this.date = date;
        this.message = message;
        this.emailFrom = emailFrom;
        this.fullNameTo = fullNameTo;
        this.avatarTo = avatarTo;
        this.emailTo = emailTo;
        this.avartar = avartar;
        this.fullName = fullName;
        this.date1 = date1;
        this.userIdTo = userIdTo;
        this.userIdFrom = userIdFrom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getFullNameTo() {
        return fullNameTo;
    }

    public void setFullNameTo(String fullNameTo) {
        this.fullNameTo = fullNameTo;
    }

    public String getAvatarTo() {
        return avatarTo;
    }

    public void setAvatarTo(String avatarTo) {
        this.avatarTo = avatarTo;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getAvartar() {
        return avartar;
    }

    public void setAvartar(String avartar) {
        this.avartar = avartar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public Integer getUserIdTo() {
        return userIdTo;
    }

    public void setUserIdTo(Integer userIdTo) {
        this.userIdTo = userIdTo;
    }

    public Integer getUserIdFrom() {
        return userIdFrom;
    }

    public void setUserIdFrom(Integer userIdFrom) {
        this.userIdFrom = userIdFrom;
    }
}
