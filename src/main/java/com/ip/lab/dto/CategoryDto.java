package com.ip.lab.dto;

import com.ip.lab.Models.Category;

public class CategoryDto {
    private final Long Id;
    private final String name;

    public CategoryDto(Category category) {
        Id = category.getId();
        this.name = category.getName();
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }
}