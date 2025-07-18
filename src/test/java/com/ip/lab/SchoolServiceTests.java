package com.ip.lab;

import com.ip.lab.Models.School;
import com.ip.lab.Models.Student;
import com.ip.lab.services.SchoolService;
import com.ip.lab.services.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SchoolServiceTests {
    @Autowired
    private SchoolService schoolService;

    @Autowired
    private StudentService studentService;

    //@Test
    public void testDeleteAllSchools() {
        schoolService.deleteAllSchools();
        int n = 3;
        for (int i = 0; i < n; i++) {
            String name = "School" + i;
            schoolService.addSchool(name);
        }
        schoolService.deleteAllSchools();
        Assertions.assertEquals(schoolService.findAllSchools().size(), 0);
    }

    //@Test
    public void testDeleteSchool() {
        schoolService.deleteAllSchools();
        String name = "School1";
        School c = schoolService.addSchool(name);
        schoolService.deleteSchool(c.getId());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            schoolService.findSchool(c.getId());});
    }

    //@Test
    public void testAddSchool() {
        schoolService.deleteAllSchools();
        String name = "School1";
        School c = schoolService.addSchool(name);
        Assertions.assertNotNull(schoolService.findSchool(c.getId()));
        schoolService.deleteAllSchools();
    }

    //@Test
    public void testUpdateSchool() {
        schoolService.deleteAllSchools();
        String name = "School1";
        School c = schoolService.addSchool(name);
        String name2 = "School2";
        schoolService.updateSchool(c.getId(), name2);
        Assertions.assertNotNull(schoolService.findSchool(c.getId()));
        schoolService.deleteAllSchools();
    }


    //@Test
    public void testFindAllSchools() {
        schoolService.deleteAllSchools();
        int n = 3;
        for (int i = 0; i < n; i++) {
            String name = "School" + i;
            schoolService.addSchool(name);
        }

        Assertions.assertEquals(schoolService.findAllSchools().size(), n);
        schoolService.deleteAllSchools();
    }

    //@Test
    public void testAddStudent() {

        studentService.deleteAllStudents();
        schoolService.deleteAllSchools();


        final String name = "School";
        School c = schoolService.addSchool(name);
        Student newStudent = studentService.addStudent("cha", "chacha", "111");
        schoolService.addNewStudent(c.getId(), newStudent);

        Assertions.assertFalse(schoolService.findSchool(c.getId()).getStudents().contains(newStudent));


        studentService.deleteAllStudents();
        schoolService.deleteAllSchools();
    }

    //@Test
    public void testDeleteStudent() {
        studentService.deleteAllStudents();
        schoolService.deleteAllSchools();


        final String name = "School";
        School c = schoolService.addSchool(name);
        Student newStudent = studentService.addStudent("cha", "chacha", "111");

        schoolService.addNewStudent(c.getId(), newStudent);
        Assertions.assertFalse(schoolService.findSchool(c.getId()).getStudents().contains(newStudent));

        schoolService.deleteStudent(c.getId(), newStudent);
        Assertions.assertFalse(schoolService.findSchool(c.getId()).getStudents().contains(newStudent));

        studentService.deleteAllStudents();
        schoolService.deleteAllSchools();

    }
}
