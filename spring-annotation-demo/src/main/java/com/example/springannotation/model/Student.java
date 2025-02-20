package com.example.springannotation.model;

import org.springframework.stereotype.Component;

@Component
public class Student {
    private int id = 131;
    private String name = "G rakesh";

    public void display() {
        System.out.println("Student ID: " + id);
        System.out.println("Student Name: " + name);
    }
}
