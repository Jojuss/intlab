package com.ip.lab.mvc;

import com.ip.lab.dto.GroupedStudAndCategoryDto;
import com.ip.lab.services.CategoryStudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/categoryStudent")
public class CategoryStudentMvcController {
    private final CategoryStudentService categoryStudentService;

    public CategoryStudentMvcController(CategoryStudentService categoryStudentService) {
        this.categoryStudentService = categoryStudentService;
    }

    @GetMapping("/groupbycategory")
    public String getGroupedStudAndCategory(Model model) {
        List<GroupedStudAndCategoryDto> GroupedStudAndCategoryDtos = categoryStudentService.getGroupedStudAndCategory();
        model.addAttribute("GroupedStudAndCategoryDtos", GroupedStudAndCategoryDtos);
        return "groupbycategory";
    }
}
