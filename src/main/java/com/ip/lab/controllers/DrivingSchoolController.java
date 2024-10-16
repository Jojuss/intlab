package com.ip.lab.controllers;

import com.ip.lab.Models.Student;
import com.ip.lab.dto.DrivingSchoolDto;
import com.ip.lab.dto.StudentDto;
import com.ip.lab.services.DrivingSchoolService;
import com.ip.lab.services.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivingSchool")
public class DrivingSchoolController {
    private final DrivingSchoolService drivingSchoolService;
    private final StudentService studentService;

    public DrivingSchoolController(DrivingSchoolService drivingSchoolService, StudentService studentService) {
        this.drivingSchoolService = drivingSchoolService;
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public DrivingSchoolDto getDrivingSchool(@PathVariable long id) {
        return new DrivingSchoolDto(drivingSchoolService.findDrivingSchool(id));
    }

    @GetMapping("/{id}/students")
    public List<StudentDto> getDrivingSchoolStudents(@PathVariable long id) {
        return drivingSchoolService.findDrivingSchool(id).getStudents().stream().map(StudentDto::new).toList();
    }

    @GetMapping
    public List<DrivingSchoolDto> getAllDrivingSchools() {
        return drivingSchoolService.findAllDrivingSchools().stream()
                .map(DrivingSchoolDto::new)
                .toList();
    }

    @PostMapping
    public DrivingSchoolDto create(@RequestParam("name") String name) {
        return new DrivingSchoolDto(drivingSchoolService.addDrivingSchool(name));
    }

    @PutMapping("/{id}")
    public DrivingSchoolDto update(@PathVariable Long id,
                                   @RequestParam("name") String name) {
        return new DrivingSchoolDto(drivingSchoolService.updateDrivingSchool(id, name));
    }

    @DeleteMapping("/{id}")
    public DrivingSchoolDto delete(@PathVariable Long id) {
        return new DrivingSchoolDto(drivingSchoolService.deleteDrivingSchool(id));
    }

    @PutMapping("/{id}/hire")
    public StudentDto hire(@PathVariable Long id, @RequestParam Long studentId) {
        Student e = studentService.findStudent(studentId);
        return new StudentDto(drivingSchoolService.addNewStudent(id, e));
    }

    @PutMapping("/{id}/dismiss")
    public void dismiss(@PathVariable Long id, @RequestParam Long studentId) {
        Student e = studentService.findStudent(studentId);
        drivingSchoolService.deleteStudent(id, e);
    }

}
