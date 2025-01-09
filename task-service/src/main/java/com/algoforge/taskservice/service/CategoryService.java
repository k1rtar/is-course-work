package com.algoforge.taskservice.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.algoforge.taskservice.model.Category;
import com.algoforge.taskservice.repository.CategoryRepository;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category createCategory(Category cat) {
        return categoryRepository.save(cat);
    }
}
