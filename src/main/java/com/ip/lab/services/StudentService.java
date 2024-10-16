package com.ip.lab.services;

import com.ip.lab.Models.Category;
import com.ip.lab.Models.School;
import com.ip.lab.Models.Student;
import com.ip.lab.dao.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public Student addStudent(String surname, String name, String phoneNumber) {
        if (!StringUtils.hasText(name) ||!StringUtils.hasText(surname) || !StringUtils.hasText(phoneNumber)) {
            throw new IllegalArgumentException("Student's data is null or empty");
        }
        final Student student = new Student(surname, name, phoneNumber);
        studentRepository.save(student);
        return student;
    }

    @Transactional
    public Student findStudent(Long id) {
        final Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            throw new EntityNotFoundException(String.format("Student with id [%s] is not found", id));
        }
        return student;
    }

    @Transactional(readOnly = true)
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }
    @Transactional(readOnly = true)
    public List<Student> findAllFreeStudents() {
        return studentRepository.findBySchoolIsNull();
    }

    @Transactional
    public Student updateStudent(Long id, String surname, String name, String phoneNumber) {
        if (!StringUtils.hasText(name) ||!StringUtils.hasText(surname) || !StringUtils.hasText(phoneNumber)) {
            throw new IllegalArgumentException("Student's data is null or empty");
        }
        final Student currentStudent = findStudent(id);
        currentStudent.setName(name);
        currentStudent.setSurname(surname);
        currentStudent.setPhoneNum(phoneNumber);

        return studentRepository.save(currentStudent);
    }

    @Transactional
    public Student deleteStudent(Long id) {
        final Student student = findStudent(id);
        studentRepository.delete(student);
        return student;
    }

    @Transactional
    public void deleteAllStudents() {
        studentRepository.deleteAll();
    }

    @Transactional
    public void addSchool(Long id, School c) {
        final Student student = findStudent(id);
        student.setSchool(c);
        studentRepository.save(student);
    }

    @Transactional
    public void deleteSchool(Long id) {
        final Student student = findStudent(id);
        student.deleteSchool();
        studentRepository.save(student);
    }

    @Transactional
    public Student addCategory(Long id, Category p) {
        Student e = findStudent(id);
        e.addNewCategory(p);
        System.out.println(e.getCategories().size());
        return studentRepository.save(e);
    }

    @Transactional
    public Student deleteCategory(Long id, Category p) {
        Student e = findStudent(id);
        e.removeCategory(p);
        return studentRepository.save(e);
    }

    @Transactional
    public List<Student> getStudentsByCategory(Category p) {
        List<Student> students = studentRepository.findByCategories_Id(p.getId());
        return students;
    }

}
