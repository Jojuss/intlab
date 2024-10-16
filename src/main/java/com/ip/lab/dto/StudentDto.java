package com.ip.lab.dto;

import com.ip.lab.Models.Student;

import java.util.List;

public class StudentDto {
    private Long Id;
    private String name;
    private String phoneNumber;
    private String surname;
    private List<CategoryDto> categories;

    private SchoolWithoutStudDto School;

    public StudentDto(Student student) {
        Id = student.getId();
        this.name = student.getName();
        this.phoneNumber = student.getPhoneNum();
        this.surname = student.getSurname();
        this.categories = student.getCategories().stream().map(CategoryDto::new).toList();
        this.School = student.getSchool() != null ? new SchoolWithoutStudDto(student.getSchool()) : null;
    }

    public StudentDto() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSurname() {
        return surname;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public SchoolWithoutStudDto getSchool() {
        return School;
    }
}
