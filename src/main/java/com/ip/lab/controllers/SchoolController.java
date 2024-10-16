package com.ip.lab.controllers;

import com.ip.lab.Models.Student;
import com.ip.lab.WebConfiguration;
import com.ip.lab.dto.SchoolDto;
import com.ip.lab.dto.StudentDto;
import com.ip.lab.services.SchoolService;
import com.ip.lab.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/School")
public class SchoolController {
    private final SchoolService schoolService;
    private final StudentService studentService;

    public SchoolController(SchoolService schoolService, StudentService studentService) {
        this.schoolService = schoolService;
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public SchoolDto getSchool(@PathVariable long id) {
        return new SchoolDto(schoolService.findSchool(id));
    }

    @GetMapping("/{id}/students")
    public List<StudentDto> getSchoolStudents(@PathVariable long id) {
        return schoolService.findSchool(id).getStudents().stream().map(StudentDto::new).toList();
    }

    @GetMapping
    public List<SchoolDto> getAllSchools() {
        return schoolService.findAllSchools().stream()
                .map(SchoolDto::new)
                .toList();
    }

    @PostMapping
    public SchoolDto create(@RequestBody @Valid SchoolDto schoolDto) {
        return new SchoolDto(schoolService.addSchool(schoolDto.getName()));
    }

    @PutMapping("/{id}")
    public SchoolDto update(@PathVariable Long id, @RequestBody @Valid SchoolDto schoolDto) {
        return new SchoolDto(schoolService.updateSchool(id, schoolDto.getName()));
    }

    @DeleteMapping("/{id}")
    public SchoolDto delete(@PathVariable Long id) {
        return new SchoolDto(schoolService.deleteSchool(id));
    }

    @PutMapping("/{id}/hire")
    public StudentDto hire(@PathVariable Long id, @RequestParam Long studentId) {
        Student e = studentService.findStudent(studentId);
        return new StudentDto(schoolService.addNewStudent(id, e));
    }

    @PutMapping("/{id}/dismiss")
    public void dismiss(@PathVariable Long id, @RequestParam Long studentId) {
        Student e = studentService.findStudent(studentId);
        schoolService.deleteStudent(id, e);
    }

}
