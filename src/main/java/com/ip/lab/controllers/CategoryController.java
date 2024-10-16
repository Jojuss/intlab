package com.ip.lab.controllers;

import com.ip.lab.WebConfiguration;
import com.ip.lab.dto.CategoryDto;
import com.ip.lab.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public CategoryDto getSchool(@PathVariable long id) {
        return new CategoryDto(categoryService.findCategory(id));
    }

    @GetMapping
    public List<CategoryDto> getAllSchools() {
        return categoryService.findAllCategories().stream()
                .map(CategoryDto::new)
                .toList();
    }

    @PostMapping
    public CategoryDto create(@RequestBody @Valid CategoryDto categoryDto) {
        return new CategoryDto(categoryService.addCategory(categoryDto.getName()));
    }

    @PutMapping("/{id}")
    public CategoryDto update(@PathVariable Long id, @RequestBody @Valid CategoryDto categoryDto) {
        return new CategoryDto(categoryService.updateCategory(id, categoryDto.getName()));
    }

    @DeleteMapping("/{id}")
    public CategoryDto delete(@PathVariable Long id) {
        return new CategoryDto(categoryService.deleteCategory(id));
    }
}