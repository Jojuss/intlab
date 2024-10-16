package com.ip.lab.dto;

import com.ip.lab.Models.School;

public class SchoolWithoutStudDto {
    private final Long Id;
    private final String name;

    public SchoolWithoutStudDto(School school) {
        this.Id = school.getId();
        this.name = school.getName();
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }
}
