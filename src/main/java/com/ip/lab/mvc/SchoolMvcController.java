package com.ip.lab.mvc;

import com.ip.lab.dto.CategoryDto;
import com.ip.lab.dto.SchoolDto;
import com.ip.lab.dto.StudentDto;
import com.ip.lab.services.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/School")
public class SchoolMvcController {
    private final SchoolService schoolService;
    private final StudentService studentService;
    private final CategoryService categoryService;

    public SchoolMvcController(SchoolService schoolService, StudentService studentService, CategoryService categoryService) {
        this.schoolService = schoolService;
        this.studentService = studentService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getSchools(Model model) {
        model.addAttribute("Schools",
                schoolService.findAllSchools().stream()
                        .map(SchoolDto::new)
                        .toList());
        return "School";
    }

    @GetMapping("/one/{id}")
    public String getSchool(@PathVariable(required = false) Long id, Model model) {
        model.addAttribute("School",
                new SchoolDto(schoolService.findSchool(id)));
        model.addAttribute("freeStudents", studentService.findAllFreeStudents().stream().map(StudentDto::new).toList());
        model.addAttribute("categories", categoryService.findAllCategories().stream().map(CategoryDto::new).toList());
        return "School-one";
    }

    @PostMapping(value = "/redirect")
    public String redirectToSchoolPage(@RequestParam("SchoolId") Long SchoolId) {
        return "redirect:/School/one/" + SchoolId;
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editSchool(@PathVariable(required = false) Long id,
                                    Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("SchoolDto", new SchoolDto());
        } else {
            model.addAttribute("SchoolId", id);
            model.addAttribute("SchoolDto", new SchoolDto(schoolService.findSchool(id)));
        }
        return "School-edit";
    }

    @PostMapping(value = {"/", "/{id}"})
    public String saveSchool(@PathVariable(required = false) Long id,
                                    @ModelAttribute @Valid SchoolDto schoolDto,
                                    BindingResult bindingResult,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "School-edit";
        }
        if (id == null || id <= 0) {
            schoolService.addSchool(schoolDto.getName());
        } else {
            schoolService.updateSchool(id, schoolDto.getName());
        }
        return "redirect:/School";
    }

    @PostMapping("/delete/{id}")
    public String deleteSchool(@PathVariable Long id) {
        schoolService.deleteSchool(id);
        return "redirect:/School";
    }
}
