package com.ip.lab.dto;

import com.ip.lab.Models.Category;

public class CategoryDto {
    private Long Id;
    private String name;

    public CategoryDto(Category category) {
        Id = category.getId();
        this.name = category.getName();
    }

    public CategoryDto() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }
}