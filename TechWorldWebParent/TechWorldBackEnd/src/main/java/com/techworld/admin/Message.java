package com.techworld.admin;

public class Message {
    private String keyword;
    private String content;

    public Message(String keyword, String content) {
        this.keyword = keyword;
        this.content = content;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
