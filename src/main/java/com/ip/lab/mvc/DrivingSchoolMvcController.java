package com.ip.lab.mvc;

import com.ip.lab.dto.CategoryDto;
import com.ip.lab.dto.DrivingSchoolDto;
import com.ip.lab.dto.StudentDto;
import com.ip.lab.services.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/drivingSchool")
public class DrivingSchoolMvcController {
    private final DrivingSchoolService drivingSchoolService;
    private final StudentService studentService;
    private final CategoryService categoryService;

    public DrivingSchoolMvcController(DrivingSchoolService drivingSchoolService, StudentService studentService, CategoryService categoryService) {
        this.drivingSchoolService = drivingSchoolService;
        this.studentService = studentService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getDrivingSchools(Model model) {
        model.addAttribute("drivingSchools",
                drivingSchoolService.findAllDrivingSchools().stream()
                        .map(DrivingSchoolDto::new)
                        .toList());
        return "drivingSchool";
    }

    @GetMapping("/one/{id}")
    public String getDrivingSchool(@PathVariable(required = false) Long id, Model model) {
        model.addAttribute("drivingSchool",
                new DrivingSchoolDto(drivingSchoolService.findDrivingSchool(id)));
        model.addAttribute("freeStudents", studentService.findAllFreeStudents().stream().map(StudentDto::new).toList());
        model.addAttribute("categories", categoryService.findAllCategories().stream().map(CategoryDto::new).toList());
        return "drivingSchool-one";
    }

    @PostMapping(value = "/redirect")
    public String redirectToDrivingSchoolPage(@RequestParam("drivingSchoolId") Long drivingSchoolId) {
        return "redirect:/drivingSchool/one/" + drivingSchoolId;
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editDrivingSchool(@PathVariable(required = false) Long id,
                                    Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("drivingSchoolDto", new DrivingSchoolDto());
        } else {
            model.addAttribute("drivingSchoolId", id);
            model.addAttribute("drivingSchoolDto", new DrivingSchoolDto(drivingSchoolService.findDrivingSchool(id)));
        }
        return "drivingSchool-edit";
    }

    @PostMapping(value = {"/", "/{id}"})
    public String saveDrivingSchool(@PathVariable(required = false) Long id,
                                    @ModelAttribute @Valid DrivingSchoolDto drivingSchoolDto,
                                    BindingResult bindingResult,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "drivingSchool-edit";
        }
        if (id == null || id <= 0) {
            drivingSchoolService.addDrivingSchool(drivingSchoolDto.getName());
        } else {
            drivingSchoolService.updateDrivingSchool(id, drivingSchoolDto.getName());
        }
        return "redirect:/drivingSchool";
    }

    @PostMapping("/delete/{id}")
    public String deleteDrivingSchool(@PathVariable Long id) {
        drivingSchoolService.deleteDrivingSchool(id);
        return "redirect:/drivingSchool";
    }
}
