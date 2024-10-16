package com.ip.lab.dao;

import com.ip.lab.Models.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long> {
    Optional<School> findById(Long id);
}
