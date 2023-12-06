package com.techworld.order;

public class OrderCancelResponse {
    private Integer orderId;

    public OrderCancelResponse(Integer orderId) {
        this.orderId = orderId;
    }

    public OrderCancelResponse() {

    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
