package com.example.springjavaconfig.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Order orderBean() {
        return new Order(101, "Laptop");
    }

    @Bean
    public Customer customerBean() {
        return new Customer(1, "John Doe", orderBean());
    }
}
