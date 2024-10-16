package com.ip.lab.dao;

import com.ip.lab.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findById(Long id);
    List<Student> findByCategories_Id(Long p_id);
    List<Student> findBySchoolIsNull();
}
