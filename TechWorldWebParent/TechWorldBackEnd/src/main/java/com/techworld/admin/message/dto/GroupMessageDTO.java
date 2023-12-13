package com.techworld.admin.message.dto;

public class GroupMessageDTO {
    private String createdTime;
    private String email;
    private String message;

    private String fullName;

    private String image;
    private String timeFormat;

    public GroupMessageDTO(String date, String email, String message, String fullName, String image, String timeFormat) {
        this.createdTime = date;
        this.email = email;
        this.message = message;
        this.fullName = fullName;
        this.image = image;
        this.timeFormat = timeFormat;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }
}
