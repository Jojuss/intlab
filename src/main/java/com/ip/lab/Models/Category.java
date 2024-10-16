package com.ip.lab.Models;
import jakarta.persistence.*;
import java.util.*;
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String name;

    @ManyToMany(mappedBy = "categories", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<Student> students = new HashSet<>();

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return Id;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public void addNewStudent(Student e) {
        students.add(e);
        if (!e.getCategories().contains(this)) {
            e.addNewCategory(this);
        }
    }
    public void deleteStudent(Student e) {
        students.remove(e);
        if (e.getCategories().contains(this)) {
            e.removeCategory(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category student = (Category) o;
        return Objects.equals(Id, student.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }
}
