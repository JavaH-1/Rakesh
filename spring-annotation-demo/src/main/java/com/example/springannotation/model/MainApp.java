package com.example.springannotation.model;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.example.springannotation.model.College;

public class MainApp {
    public static void main(String[] args) {
        // Load Spring Context
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Get College Bean
        College college = context.getBean(College.class);
        college.showDetails();

        // Close context
        ((AnnotationConfigApplicationContext) context).close();
    }
}
