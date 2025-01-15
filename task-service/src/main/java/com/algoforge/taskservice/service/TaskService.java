package com.algoforge.taskservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.algoforge.taskservice.model.Task;
import com.algoforge.taskservice.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * Поиск задач с фильтрами + пагинация на уровне логики приложения.
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
        // Получаем все задачи
        List<Task> allTasks = taskRepository.findAll();

        // Применяем фильтры
        List<Task> filteredTasks = allTasks.stream()
                .filter(task -> {
                    boolean matches = true;

                    // Фильтр по title (нечувствительный к регистру)
                    if (title != null && !title.isBlank()) {
                        matches = task.getTitle().toLowerCase().contains(title.toLowerCase());
                    }

                    // Фильтр по categoryId
                    if (matches && categoryId != null) {
                        matches = task.getCategories().stream()
                                .anyMatch(category -> category.getCategoryId().equals(categoryId));
                    }

                    // Фильтр по minDifficulty
                    if (matches && minDifficulty != null) {
                        matches = task.getDifficultyLevel().ordinal() >= minDifficulty;
                    }

                    // Фильтр по maxDifficulty
                    if (matches && maxDifficulty != null) {
                        matches = task.getDifficultyLevel().ordinal() <= maxDifficulty;
                    }

                    return matches;
                })
                .collect(Collectors.toList());

        // Пагинация
        int start = Math.min(page * size, filteredTasks.size());
        int end = Math.min(start + size, filteredTasks.size());
        List<Task> pagedTasks = filteredTasks.subList(start, end);

        return new PageImpl<>(pagedTasks, PageRequest.of(page, size), filteredTasks.size());
    }

    public List<Task> getTasksByCreator(Long userId) {
        return taskRepository.findByCreatorUserId(userId);
    }
}
