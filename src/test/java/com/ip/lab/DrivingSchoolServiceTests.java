package com.ip.lab;

import com.ip.lab.Models.DrivingSchool;
import com.ip.lab.Models.Student;
import com.ip.lab.services.DrivingSchoolService;
import com.ip.lab.services.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DrivingSchoolServiceTests {
    @Autowired
    private DrivingSchoolService drivingSchoolService;

    @Autowired
    private StudentService studentService;

    @Test
    public void testDeleteAllDrivingSchools() {
        drivingSchoolService.deleteAllDrivingSchools();
        int n = 3;
        for (int i = 0; i < n; i++) {
            String name = "DrivingSchool" + i;
            drivingSchoolService.addDrivingSchool(name);
        }
        drivingSchoolService.deleteAllDrivingSchools();
        Assertions.assertEquals(drivingSchoolService.findAllDrivingSchools().size(), 0);
    }

    @Test
    public void testDeleteDrivingSchool() {
        drivingSchoolService.deleteAllDrivingSchools();
        String name = "DrivingSchool1";
        DrivingSchool c = drivingSchoolService.addDrivingSchool(name);
        drivingSchoolService.deleteDrivingSchool(c.getId());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            drivingSchoolService.findDrivingSchool(c.getId());});
    }

    @Test
    public void testAddDrivingSchool() {
        drivingSchoolService.deleteAllDrivingSchools();
        String name = "DrivingSchool1";
        DrivingSchool c = drivingSchoolService.addDrivingSchool(name);
        Assertions.assertNotNull(drivingSchoolService.findDrivingSchool(c.getId()));
        drivingSchoolService.deleteAllDrivingSchools();
    }

    @Test
    public void testUpdateDrivingSchool() {
        drivingSchoolService.deleteAllDrivingSchools();
        String name = "DrivingSchool1";
        DrivingSchool c = drivingSchoolService.addDrivingSchool(name);
        String name2 = "DrivingSchool2";
        drivingSchoolService.updateDrivingSchool(c.getId(), name2);
        Assertions.assertNotNull(drivingSchoolService.findDrivingSchool(c.getId()));
        drivingSchoolService.deleteAllDrivingSchools();
    }


    @Test
    public void testFindAllDrivingSchools() {
        drivingSchoolService.deleteAllDrivingSchools();
        int n = 3;
        for (int i = 0; i < n; i++) {
            String name = "DrivingSchool" + i;
            drivingSchoolService.addDrivingSchool(name);
        }

        Assertions.assertEquals(drivingSchoolService.findAllDrivingSchools().size(), n);
        drivingSchoolService.deleteAllDrivingSchools();
    }

    @Test
    public void testAddStudent() {

        studentService.deleteAllStudents();
        drivingSchoolService.deleteAllDrivingSchools();


        final String name = "DrivingSchool";
        DrivingSchool c = drivingSchoolService.addDrivingSchool(name);
        Student newStudent = studentService.addStudent("cha", "chacha", "111");
        drivingSchoolService.addNewStudent(c.getId(), newStudent);

        Assertions.assertFalse(drivingSchoolService.findDrivingSchool(c.getId()).getStudents().contains(newStudent));


        studentService.deleteAllStudents();
        drivingSchoolService.deleteAllDrivingSchools();
    }

    @Test
    public void testDeleteStudent() {
        studentService.deleteAllStudents();
        drivingSchoolService.deleteAllDrivingSchools();


        final String name = "DrivingSchool";
        DrivingSchool c = drivingSchoolService.addDrivingSchool(name);
        Student newStudent = studentService.addStudent("cha", "chacha", "111");

        drivingSchoolService.addNewStudent(c.getId(), newStudent);
        Assertions.assertFalse(drivingSchoolService.findDrivingSchool(c.getId()).getStudents().contains(newStudent));

        drivingSchoolService.deleteStudent(c.getId(), newStudent);
        Assertions.assertFalse(drivingSchoolService.findDrivingSchool(c.getId()).getStudents().contains(newStudent));

        studentService.deleteAllStudents();
        drivingSchoolService.deleteAllDrivingSchools();

    }
}
