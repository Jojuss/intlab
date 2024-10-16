package com.ip.lab.services;

import com.ip.lab.Models.School;
import com.ip.lab.Models.Student;
import com.ip.lab.dao.SchoolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class SchoolService {

    private SchoolRepository schoolRepository;
    private StudentService studentService;

    public SchoolService(SchoolRepository schoolRepository, StudentService studentService) {
        this.schoolRepository = schoolRepository;
        this.studentService = studentService;
    }

    @Transactional
    public School addSchool(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Student name is null or empty");
        }
        final School school = new School(name);
        schoolRepository.save(school);
        return school;
    }

    @Transactional(readOnly = true)
    public School findSchool(Long id) {
        final School school = schoolRepository.findById(id).orElse(null);
        if (school == null) {
            throw new EntityNotFoundException(String.format("School with id [%s] is not found", id));
        }
        return school;
    }

    @Transactional(readOnly = true)
    public List<School> findAllSchools() {
        return schoolRepository.findAll();
    }

    @Transactional
    public School updateSchool(Long id, String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("School name is null or empty");
        }
        final School currentSchool = findSchool(id);
        currentSchool.setName(name);

        return schoolRepository.save(currentSchool);
    }

    @Transactional
    public School deleteSchool(Long id) {
        final School currentSchool = findSchool(id);
        schoolRepository.delete(currentSchool);
        return currentSchool;
    }

    @Transactional
    public void deleteAllSchools() {
        schoolRepository.deleteAll();
    }

    @Transactional
    public Student addNewStudent(Long id, Student student) {
        School currentSchool = findSchool(id);
        currentSchool.addNewStudent(student);
        schoolRepository.save(currentSchool);
        return student;
    }

    @Transactional
    public void deleteStudent(Long id, Student student) {
        School currentSchool = findSchool(id);
        currentSchool.deleteStudent(student);
    }
}
