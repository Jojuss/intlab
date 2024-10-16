package com.ip.lab.dto;

public class GroupedStudAndCategoryDto {
    private String categoryName;
    private Long StudentsCount;

    public GroupedStudAndCategoryDto(String categoryName, Long StudentsCount){
        this.categoryName=categoryName;
        this.StudentsCount=StudentsCount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryId(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getStudentsCount() {
        return StudentsCount;
    }

    public void setStudentsCount(Long studentsCount) {
        StudentsCount = studentsCount;
    }
}
