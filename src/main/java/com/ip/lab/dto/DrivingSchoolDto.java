package com.ip.lab.dto;

import com.ip.lab.Models.DrivingSchool;

import java.util.List;

public class DrivingSchoolDto {
    private final Long Id;
    private final String name;
    private final List<StudentDto> students;

    public DrivingSchoolDto(DrivingSchool drivingSchool) {
        Id = drivingSchool.getId();
        this.name = drivingSchool.getName();
        this.students = drivingSchool.getStudents().stream().map(StudentDto::new).toList();
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
