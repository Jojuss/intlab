package com.ip.lab.controllers;

import com.ip.lab.Models.Category;
import com.ip.lab.dto.StudentDto;
import com.ip.lab.services.CategoryService;
import com.ip.lab.services.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    private final CategoryService categoryService;

    public StudentController(StudentService studentService, CategoryService categoryService) {
        this.studentService = studentService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public StudentDto getStudent(@PathVariable long id) {
        return new StudentDto(studentService.findStudent(id));
    }

    @GetMapping
    public List<StudentDto> getAllStudents() {
        return studentService.findAllStudents().stream()
                .map(StudentDto::new)
                .toList();
    }

    @PostMapping
    public StudentDto createStudent(@RequestParam("name") String name,
                                    @RequestParam("surname") String surname,
                                    @RequestParam("phoneNumber") String phoneNumber) {
        return new StudentDto(studentService.addStudent(
                surname,
                name,
                phoneNumber));
    }

    @PutMapping("/{id}")
    public StudentDto updateStudent(@PathVariable Long id,
                                    @RequestParam("name") String name,
                                    @RequestParam("surname") String surname,
                                    @RequestParam("phoneNumber") String phoneNumber) {
        return new StudentDto(studentService.updateStudent(id, surname, name, phoneNumber));
    }

    @DeleteMapping("/{id}")
    public StudentDto deleteStudent(@PathVariable Long id) {
        return new StudentDto(studentService.deleteStudent(id));
    }

    @PutMapping("/{id}/addCat")
    public StudentDto addCategory(@PathVariable Long id,
                                  @RequestParam("category") Long category) {
        Category p = categoryService.findCategory(category);
        if (p == null)
            return null;
        return new StudentDto(studentService.addCategory(id, p));
    }
    @PutMapping("/{id}/delCat")
    public StudentDto delCategory(@PathVariable Long id,
                                  @RequestParam("category") Long category) {
        Category p = categoryService.findCategory(category);
        if (p == null)
            return null;
        return new StudentDto(studentService.deleteCategory(id, p));
    }

    @GetMapping("/free")
    public List<StudentDto> getAllFreeStudents() {
        return studentService.findAllFreeStudents().stream()
                .map(StudentDto::new)
                .toList();
    }

    @GetMapping("/category")
    public List<StudentDto> getStudentsByCategory(@RequestParam Long cat) {
        Category p = categoryService.findCategory(cat);
        if (p == null) {
            return null;
        }

        return studentService.getStudentsByCategory(p).stream()
                .map(StudentDto::new)
                .toList();
    }
}
