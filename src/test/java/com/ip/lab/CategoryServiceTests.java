package com.ip.lab;

import com.ip.lab.Models.Category;
import com.ip.lab.Models.Student;
import com.ip.lab.services.CategoryService;
import com.ip.lab.services.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryServiceTests {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StudentService studentService;


    //@Test
    public void testAddStudent() {
        studentService.deleteAllStudents();
        categoryService.deleteAllCategories();

        Category p = categoryService.addCategory("Category");
        Student e = studentService.addStudent("1", "2", "33");

        studentService.addCategory(e.getId(), p);
        Assertions.assertTrue(studentService.findStudent(e.getId()).getCategories().contains(p));
        Assertions.assertTrue(categoryService.findCategory(p.getId()).getStudents().contains(e));

        studentService.deleteAllStudents();
        categoryService.deleteAllCategories();

    }

    //@Test
    public void testDeleteStudent() {
        studentService.deleteAllStudents();
        categoryService.deleteAllCategories();

        Category p = categoryService.addCategory("Category");
        Student e = studentService.addStudent("1", "2", "33");

        studentService.addCategory(e.getId(), p);
        studentService.deleteCategory(e.getId(), p);
        Assertions.assertFalse(studentService.findStudent(e.getId()).getCategories().contains(p));
        Assertions.assertFalse(categoryService.findCategory(p.getId()).getStudents().contains(e));

        studentService.deleteAllStudents();
        categoryService.deleteAllCategories();

    }
}
