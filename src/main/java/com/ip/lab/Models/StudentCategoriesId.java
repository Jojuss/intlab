package com.ip.lab.Models;

import java.io.Serializable;

public class StudentCategoriesId implements Serializable {
    private Long student;
    private Long category;

    public Long getStudent() {
        return student;
    }

    public void setStudent(Long student) {
        this.student = student;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }
}