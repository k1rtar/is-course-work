package com.algoforge.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.algoforge.taskservice.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
