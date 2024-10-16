package com.ip.lab.dto;

import com.ip.lab.Models.Student;

import java.util.List;

public class StudentDto {
    private final Long Id;
    private final String name;
    private final String phoneNumber;
    private final String surname;
    private final List<CategoryDto> categories;

    private final SchoolWithoutStudDto School;

    public StudentDto(Student student) {
        Id = student.getId();
        this.name = student.getName();
        this.phoneNumber = student.getPhoneNum();
        this.surname = student.getSurname();
        this.categories = student.getCategories().stream().map(CategoryDto::new).toList();
        this.School = student.getSchool() != null ? new SchoolWithoutStudDto(student.getSchool()) : null;
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
