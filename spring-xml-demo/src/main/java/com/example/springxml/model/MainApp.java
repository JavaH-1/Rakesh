package com.example.springxml.model;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.example.springxml.model.Employee;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        Employee emp = (Employee) context.getBean("employeeBean");
        emp.display();

        ((ClassPathXmlApplicationContext) context).close();
    }
}
