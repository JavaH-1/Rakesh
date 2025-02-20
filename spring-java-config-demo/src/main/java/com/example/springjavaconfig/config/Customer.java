package com.example.springjavaconfig.config;

public class Customer {
    private int id;
    private String name;
    private Order order;

    public Customer(int id, String name, Order order) {
        this.id = id;
        this.name = name;
        this.order = order;
    }

    public void displayInfo() {
        System.out.println("Customer ID: " + id);
        System.out.println("Customer Name: " + name);
        System.out.println("Order Details: " + order.getOrderId() + " - " + order.getProduct());
    }
}
