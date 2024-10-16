package com.ip.lab.dao;

import com.ip.lab.Models.StudentCategories;
import com.ip.lab.dto.GroupedStudAndCategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentCategoriesRepository extends JpaRepository<StudentCategories, Long> {

    Optional<StudentCategories> findById(Long id);

    @Query("SELECT new com.ip.lab.dto.GroupedStudAndCategoryDto(cs.category.name,COUNT(cs.student.id))"+
            "FROM StudentCategories cs "+
            "group by cs.category.name")
    List<GroupedStudAndCategoryDto> getGroupedStudAndCategory();
}
