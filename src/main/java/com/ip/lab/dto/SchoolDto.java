package com.ip.lab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ip.lab.Models.School;

import java.util.List;

public class SchoolDto {
    private Long Id;
    private String name;
    private List<StudentDto> students;

    public SchoolDto(School school) {
        Id = school.getId();
        this.name = school.getName();
        this.students = school.getStudents().stream().map(StudentDto::new).toList();
    }

    public SchoolDto() {

    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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
