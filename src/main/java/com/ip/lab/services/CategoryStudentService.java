package com.ip.lab.services;

import com.ip.lab.dao.StudentCategoriesRepository;
import com.ip.lab.dto.GroupedStudAndCategoryDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryStudentService {

    private StudentCategoriesRepository studentCategoriesRepository;

    public CategoryStudentService(StudentCategoriesRepository studentCategoriesRepository) {
        this.studentCategoriesRepository = studentCategoriesRepository;
    }

    @Transactional(readOnly = true)
    public List<GroupedStudAndCategoryDto> getGroupedStudAndCategory() {
        return studentCategoriesRepository.getGroupedStudAndCategory();
    }
}
