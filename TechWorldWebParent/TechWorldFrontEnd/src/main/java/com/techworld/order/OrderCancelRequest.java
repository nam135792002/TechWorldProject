package com.techworld.order;

public class OrderCancelRequest {
    private Integer orderId;
    private String note;

    public OrderCancelRequest() {
    }

    public OrderCancelRequest(Integer orderId, String note) {
        this.orderId = orderId;
        this.note = note;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
