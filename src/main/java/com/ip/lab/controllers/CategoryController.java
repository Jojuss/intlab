package com.ip.lab.controllers;

import com.ip.lab.dto.CategoryDto;
import com.ip.lab.services.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public CategoryDto getDrivingSchool(@PathVariable long id) {
        return new CategoryDto(categoryService.findCategory(id));
    }

    @GetMapping
    public List<CategoryDto> getAllDrivingSchools() {
        return categoryService.findAllCategories().stream()
                .map(CategoryDto::new)
                .toList();
    }

    @PostMapping
    public CategoryDto create(@RequestParam("name") String name) {
        return new CategoryDto(categoryService.addCategory(name));
    }

    @PutMapping("/{id}")
    public CategoryDto update(@PathVariable Long id,
                              @RequestParam("name") String name) {
        return new CategoryDto(categoryService.updateCategory(id, name));
    }

    @DeleteMapping("/{id}")
    public CategoryDto delete(@PathVariable Long id) {
        return new CategoryDto(categoryService.deleteCategory(id));
    }
}