package com.ip.lab.dto;

import com.ip.lab.Models.DrivingSchool;

public class DrivingSchoolWithoutStudDto {
    private final Long Id;
    private final String name;

    public DrivingSchoolWithoutStudDto(DrivingSchool drivingSchool) {
        this.Id = drivingSchool.getId();
        this.name = drivingSchool.getName();
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }
}
