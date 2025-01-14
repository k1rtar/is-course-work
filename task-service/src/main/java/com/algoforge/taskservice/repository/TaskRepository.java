package com.algoforge.taskservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.algoforge.taskservice.model.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByIsPublicTrue();
    List<Task> findByCreatorUserId(Long creatorUserId);


    @Query("""
        SELECT DISTINCT t
        FROM Task t
             LEFT JOIN t.categories c
        WHERE
          (:title IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%')))
          AND (:categoryId IS NULL OR c.categoryId = :categoryId)
          AND (:minDifficulty IS NULL OR t.difficultyLevel >= :minDifficulty)
          AND (:maxDifficulty IS NULL OR t.difficultyLevel <= :maxDifficulty)
    """)
    Page<Task> searchTasks(
            @Param("title") String title,
            @Param("categoryId") Long categoryId,
            @Param("minDifficulty") Integer minDifficulty,
            @Param("maxDifficulty") Integer maxDifficulty,
            Pageable pageable
    );
}
