package com.ip.lab.services;

import com.ip.lab.Models.DrivingSchool;
import com.ip.lab.Models.Student;
import com.ip.lab.dao.DrivingSchoolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class DrivingSchoolService {

    private DrivingSchoolRepository drivingSchoolRepository;
    private StudentService studentService;

    public DrivingSchoolService(DrivingSchoolRepository drivingSchoolRepository, StudentService studentService) {
        this.drivingSchoolRepository = drivingSchoolRepository;
        this.studentService = studentService;
    }

    @Transactional
    public DrivingSchool addDrivingSchool(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Student name is null or empty");
        }
        final DrivingSchool drivingSchool = new DrivingSchool(name);
        drivingSchoolRepository.save(drivingSchool);
        return drivingSchool;
    }

    @Transactional(readOnly = true)
    public DrivingSchool findDrivingSchool(Long id) {
        final DrivingSchool drivingSchool = drivingSchoolRepository.findById(id).orElse(null);
        if (drivingSchool == null) {
            throw new EntityNotFoundException(String.format("DrivingSchool with id [%s] is not found", id));
        }
        return drivingSchool;
    }

    @Transactional(readOnly = true)
    public List<DrivingSchool> findAllDrivingSchools() {
        return drivingSchoolRepository.findAll();
    }

    @Transactional
    public DrivingSchool updateDrivingSchool(Long id, String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("DrivingSchool name is null or empty");
        }
        final DrivingSchool currentDrivingSchool = findDrivingSchool(id);
        currentDrivingSchool.setName(name);

        return drivingSchoolRepository.save(currentDrivingSchool);
    }

    @Transactional
    public DrivingSchool deleteDrivingSchool(Long id) {
        final DrivingSchool currentDrivingSchool = findDrivingSchool(id);
        drivingSchoolRepository.delete(currentDrivingSchool);
        return currentDrivingSchool;
    }

    @Transactional
    public void deleteAllDrivingSchools() {
        drivingSchoolRepository.deleteAll();
    }

    @Transactional
    public Student addNewStudent(Long id, Student student) {
        DrivingSchool currentDrivingSchool = findDrivingSchool(id);
        currentDrivingSchool.addNewStudent(student);
        drivingSchoolRepository.save(currentDrivingSchool);
        return student;
    }

    @Transactional
    public void deleteStudent(Long id, Student student) {
        DrivingSchool currentDrivingSchool = findDrivingSchool(id);
        currentDrivingSchool.deleteStudent(student);
    }
}
