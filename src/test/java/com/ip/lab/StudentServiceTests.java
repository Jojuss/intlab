package com.ip.lab;

import com.ip.lab.Models.*;
import com.ip.lab.services.CategoryService;
import com.ip.lab.services.SchoolService;
import com.ip.lab.services.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentServiceTests {
    @Autowired
    private StudentService studentService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void testAddStudent() {
        studentService.deleteAllStudents();

        studentService.addStudent("name", "surname", "111");
        Assertions.assertEquals(1, studentService.findAllStudents().size());
        studentService.deleteAllStudents();
    }

    @Test
    public void testUpdateStudent() {
        studentService.deleteAllStudents();
        final String newName = "name";

        Student e  = studentService.addStudent("surname", "name", "111");
        studentService.updateStudent(e.getId(),e.getSurname(), newName, e.getPhoneNum());
        Assertions.assertEquals(newName, studentService.findStudent(e.getId()).getName());
        studentService.deleteAllStudents();
    }

    @Test
    public void testDeleteStudent() {
        studentService.deleteAllStudents();

        Student e = studentService.addStudent("name", "surname", "111");
        studentService.deleteStudent(e.getId());
        Assertions.assertEquals(0, studentService.findAllStudents().size());
        studentService.deleteAllStudents();
    }

    @Test
    public void testFindAllStudent() {
        studentService.deleteAllStudents();
        int n = 3;
        for (int i = 0; i < n; i++) {
            studentService.addStudent("name", "surname", "111");
        }
        Assertions.assertEquals(n, studentService.findAllStudents().size());
        studentService.deleteAllStudents();
    }

    @Test
    public void testDeleteFromDrivingSchool() {
        studentService.deleteAllStudents();
        schoolService.deleteAllSchools();

        Student e = studentService.addStudent("name", "surname", "111");
        School c = schoolService.addSchool("Comp");

        studentService.addSchool(e.getId(), c);
        Assertions.assertEquals(c, studentService.findStudent(e.getId()).getSchool());

        studentService.deleteSchool(e.getId());
        Assertions.assertNull(studentService.findStudent(e.getId()).getSchool());

        studentService.deleteAllStudents();
        schoolService.deleteAllSchools();
    }

    @Test
    public void testGetByCategory() {
        studentService.deleteAllStudents();
        categoryService.deleteAllCategories();

        Category p = categoryService.addCategory("Category 1");
        Category p2 = categoryService.addCategory("Category 2");

        final int n = 10;
        for (int i = 0; i < n; i++) {
            Student e = studentService.addStudent("1111", "name" + i, "surname");
            if (i % 2 == 0) {
                studentService.addCategory(e.getId(), p);
            }
            else {
                studentService.addCategory(e.getId(), p2);
            }
        }

        Assertions.assertEquals(n / 2, studentService.getStudentsByCategory(p).size());
        Assertions.assertEquals(n / 2, studentService.getStudentsByCategory(p2).size());

        studentService.deleteAllStudents();
        categoryService.deleteAllCategories();
    }
}