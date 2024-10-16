package com.ip.lab.mvc;

import com.ip.lab.dto.StudentDto;
import com.ip.lab.services.CategoryService;
import com.ip.lab.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student")
public class StudentMvcController {
    private final StudentService studentService;
    private final CategoryService categoryService;

    public StudentMvcController(StudentService studentService, CategoryService categoryService) {
        this.studentService = studentService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getStudents(Model model) {
        model.addAttribute("students",
                studentService.findAllStudents().stream()
                        .map(StudentDto::new)
                        .toList());
        return "student";
    }

    @GetMapping(value = {"/edit/", "/edit/{id}"})
    public String editDrivingSchool(@PathVariable(required = false) Long id,
                                    Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("studentDto", new StudentDto());
        } else {
            model.addAttribute("studentId", id);
            model.addAttribute("studentDto", new StudentDto(studentService.findStudent(id)));
        }
        return "student-edit";
    }

    @PostMapping(value = {"/", "/{id}"})
    public String saveDrivingSchool(@PathVariable(required = false) Long id,
                                    @ModelAttribute @Valid StudentDto studentDto,
                                    BindingResult bindingResult,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "student-edit";
        }
        if (id == null || id <= 0) {
            studentService.addStudent(studentDto.getSurname(), studentDto.getName(), studentDto.getPhoneNumber());
        } else {
            studentService.updateStudent(id, studentDto.getSurname(), studentDto.getName(), studentDto.getPhoneNumber());
        }
        return "redirect:/student";
    }


    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/student";
    }

}