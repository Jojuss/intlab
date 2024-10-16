package com.ip.lab.Models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class School {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "school", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Student> students = new HashSet<>();

    public School(String name, Set<Student> students) {
        this.name = name;
        this.students = students;
    }

    public School(String name) {
        this.name = name;
    }

    public School() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addNewStudent(Student student) {
        students.add(student);
        student.setSchool(this);
    }

    public void deleteStudent(Student student) {
        students.remove(student);
        student.deleteSchool();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return Objects.equals(Id, school.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    @Override
    public String toString() {
        return "School{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", students=" + students +
                '}';
    }
}
