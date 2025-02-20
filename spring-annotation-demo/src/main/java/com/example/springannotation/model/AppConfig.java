package com.example.springannotation.model;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example.springannotation")
public class AppConfig {
}
