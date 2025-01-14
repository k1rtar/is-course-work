package com.algoforge.taskservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.algoforge.taskservice.model.Task;
import com.algoforge.taskservice.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task) {
        if (task.getCreationDate() == null) {
            task.setCreationDate(LocalDateTime.now());
        }
        if (task.getStatus() == null) {
            task.setStatus("Pending");
        }
        return taskRepository.save(task);
    }

    public List<Task> getAllPublicTasks() {
        return taskRepository.findByIsPublicTrue();
    }

    public Optional<Task> findById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    public Task updateTask(Long taskId, Task updates) {
        Task existing = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));

        existing.setTitle(updates.getTitle());
        existing.setDescription(updates.getDescription());
        existing.setDifficultyLevel(updates.getDifficultyLevel());
        existing.setPublic(updates.isPublic());
        existing.setStatus(updates.getStatus());
        existing.setMaxExecutionTime(updates.getMaxExecutionTime());
        existing.setMaxMemoryUsage(updates.getMaxMemoryUsage());

        return taskRepository.save(existing);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    /**
     * Поиск задач c фильтрами + пагинация.
     * Если title пустая строка, приравняем к null (не фильтровать).
     */
    public Page<Task> searchTasks(
            String title,
            Long categoryId,
            Integer minDifficulty,
            Integer maxDifficulty,
            int page,
            int size
    ) {
        if (title != null && title.isBlank()) {
            title = null;
        }

        var pageable = PageRequest.of(page, size);

        return taskRepository.searchTasks(
                title,
                categoryId,
                minDifficulty,
                maxDifficulty,
                pageable
        );
    }
    public List<Task> getTasksByCreator(Long userId) {
        return taskRepository.findByCreatorUserId(userId);
    }
}
