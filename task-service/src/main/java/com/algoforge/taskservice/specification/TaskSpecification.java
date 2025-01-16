package com.algoforge.taskservice.specification;

import com.algoforge.taskservice.model.Task;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

public class TaskSpecification {

    public static Specification<Task> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Task> hasCategoryId(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            Join<Object, Object> categories = root.join("categories", JoinType.LEFT);
            return criteriaBuilder.equal(categories.get("categoryId"), categoryId);
        };
    }

    public static Specification<Task> hasMinDifficulty(Integer minDifficulty) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.greaterThanOrEqualTo(root.get("difficultyLevel").as(Integer.class), minDifficulty);
    }

    public static Specification<Task> hasMaxDifficulty(Integer maxDifficulty) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.lessThanOrEqualTo(root.get("difficultyLevel").as(Integer.class), maxDifficulty);
    }
}
