package com.algoforge.taskservice.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.algoforge.taskservice.model.Category;
import com.algoforge.taskservice.service.CategoryService;
import java.util.List;

@RestController
@RequestMapping("/api/tasks/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @PostMapping
    public Category createCategory(@RequestBody Category cat) {
        return categoryService.createCategory(cat);
    }
}
