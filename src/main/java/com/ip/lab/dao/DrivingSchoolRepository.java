package com.ip.lab.dao;

import com.ip.lab.Models.DrivingSchool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DrivingSchoolRepository extends JpaRepository<DrivingSchool, Long> {
    Optional<DrivingSchool> findById(Long id);
}
