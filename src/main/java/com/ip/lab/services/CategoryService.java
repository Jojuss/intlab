package com.ip.lab.services;

import com.ip.lab.Models.Category;
import com.ip.lab.dao.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Category addCategory(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Student's data is null or empty");
        }
        final Category category = new Category(name);
        categoryRepository.save(category);
        return category;
    }

    @Transactional
    public Category findCategory(Long id) {
        final Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new EntityNotFoundException(String.format("Category with id [%s] is not found", id));
        }
        return category;
    }

    @Transactional(readOnly = true)
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category updateCategory(Long id, String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Student's data is null or empty");
        }
        final Category currentCategory = findCategory(id);
        currentCategory.setName(name);

        return categoryRepository.save(currentCategory);
    }

    @Transactional
    public Category deleteCategory(Long id) {
        final Category p = findCategory(id);
        categoryRepository.delete(p);
        return p;
    }

    @Transactional
    public void deleteAllCategories() {
        categoryRepository.deleteAll();
    }

}