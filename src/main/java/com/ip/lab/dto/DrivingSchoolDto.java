package com.ip.lab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ip.lab.Models.DrivingSchool;

import java.util.List;

public class DrivingSchoolDto {
    private Long Id;
    private String name;
    private List<StudentDto> students;

    public DrivingSchoolDto(DrivingSchool drivingSchool) {
        Id = drivingSchool.getId();
        this.name = drivingSchool.getName();
        this.students = drivingSchool.getStudents().stream().map(StudentDto::new).toList();
    }

    public DrivingSchoolDto() {

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
