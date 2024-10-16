package com.ip.lab.Models;

import jakarta.persistence.*;

@Entity
@Table(name="student_categories")
@IdClass(StudentCategoriesId.class)
public class StudentCategories {

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Id
    @ManyToOne
    @JoinColumn(name="students_id")
    private Student student;

    @Id
    @ManyToOne
    @JoinColumn(name="categories_id")
    private Category category;

}
