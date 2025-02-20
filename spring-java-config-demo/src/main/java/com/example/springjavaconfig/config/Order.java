package com.example.springjavaconfig.config;

public class Order {
    private int orderId;
    private String product;

    public Order(int orderId, String product) {
        this.orderId = orderId;
        this.product = product;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getProduct() {
        return product;
    }
}
