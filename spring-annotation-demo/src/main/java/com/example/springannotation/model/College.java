package com.example.springannotation.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class College {

    @Autowired
    private Student student;

    public void showDetails() {
        System.out.println("College: SRM University");
        student.display();
    }
}
