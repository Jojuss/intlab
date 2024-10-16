package com.ip.lab.controllers;

import com.ip.lab.WebConfiguration;
import com.ip.lab.dto.GroupedStudAndCategoryDto;
import com.ip.lab.services.CategoryStudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/categoryStudent")
public class CategoryStudentController {

    private CategoryStudentService categoryStudentService;

    public CategoryStudentController(CategoryStudentService categoryStudentService) {
        this.categoryStudentService = categoryStudentService;
    }

    @GetMapping("/groupbycategory")
    public List<GroupedStudAndCategoryDto> getGroupedStudAndCategory(){
        return categoryStudentService.getGroupedStudAndCategory();
    }
}
