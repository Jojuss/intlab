package com.ip.lab.dto;

import com.ip.lab.Models.School;

import java.util.List;

public class SchoolDto {
    private final Long Id;
    private final String name;
    private final List<StudentDto> students;

    public SchoolDto(School school) {
        Id = school.getId();
        this.name = school.getName();
        this.students = school.getStudents().stream().map(StudentDto::new).toList();
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public List<StudentDto> getStudents() {
        return students;
    }
}
