package com.algoforge.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.algoforge.taskservice.model.Task;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByIsPublicTrue();

    List<Task> findByCreatorUserId(Long creatorUserId);
}
