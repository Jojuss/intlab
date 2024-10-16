package com.ip.lab.Models;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String phoneNum;
    private String name;
    private String surname;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Category> categories = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private School school;

    public Student(String surname, String name, String phoneNum) {
        this.phoneNum = phoneNum;
        this.name = name;
        this.surname = surname;
    }

    public Student() {}

    public Long getId() { return id; }
    public String getPhoneNum() { return phoneNum; }
    public void setPhoneNum(String phoneNum) { this.phoneNum = phoneNum; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Category> getCategories() {
        return categories.stream().toList();
    }

    public void addNewCategory(Category p) {
        categories.add(p);
        if (!p.getStudents().contains(this)) {
            p.addNewStudent(this);
        }

    }

    public void removeCategory(Category p) {
        categories.remove(p);
        if (p.getStudents().contains(this)) {
            p.deleteStudent(this);
        }
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
        if (!school.getStudents().contains(this)) {
            school.addNewStudent(this);
        }
    }

    public void deleteSchool() {
        if (school.getStudents().contains(this)) {
            school.deleteStudent(this);
        }
        this.school = null;
        this.categories.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student e = (Student) o;
        return Objects.equals(id, e.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
