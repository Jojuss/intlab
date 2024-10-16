package com.ip.lab.Models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class DrivingSchool {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "drivingSchool", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Student> students = new HashSet<>();

    public DrivingSchool(String name, Set<Student> students) {
        this.name = name;
        this.students = students;
    }

    public DrivingSchool(String name) {
        this.name = name;
    }

    public DrivingSchool() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addNewStudent(Student student) {
        students.add(student);
        student.setDrivingSchool(this);
    }

    public void deleteStudent(Student student) {
        students.remove(student);
        student.deleteDrivingSchool();
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
        DrivingSchool drivingSchool = (DrivingSchool) o;
        return Objects.equals(Id, drivingSchool.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    @Override
    public String toString() {
        return "DrivingSchool{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", students=" + students +
                '}';
    }
}
