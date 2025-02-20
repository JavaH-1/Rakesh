package com.example.springjavaconfig.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.example.springjavaconfig.config.AppConfig;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);          // Load Spring Java Configuration


        Customer customer = context.getBean(Customer.class);           // Get Customer Bean

        customer.displayInfo();

        ((AnnotationConfigApplicationContext) context).close();           // Get Customer Bean

    } 
}
