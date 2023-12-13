package com.techworld.admin.message.dto;

public class MessageGroupDTO extends MessageDTO{

    private int groupId;

    public MessageGroupDTO(String email, String message, int groupId) {
        super(email, message);
        this.groupId = groupId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
