package com.algoforge.taskservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.algoforge.common.auth.UserPrincipal;
import com.algoforge.common.dto.TaskDto;
import com.algoforge.taskservice.model.Task;
import com.algoforge.taskservice.model.TestCase;
import com.algoforge.taskservice.service.TaskService;
import com.algoforge.taskservice.service.TestCaseService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired 
    private TestCaseService testCaseService;


    @GetMapping("/public")
    public List<TaskDto> listPublicTasks() {
        return taskService.getAllPublicTasks().stream().map(el -> el.getDtoObject()).toList();
    }

    @GetMapping("/{taskId}")
    public TaskDto getTaskById(@PathVariable Long taskId) {
        Task task = taskService.findById(taskId)
                .orElseThrow(() -> new RuntimeException("No Task found with ID=" + taskId));
        return task.getDtoObject();
    }

    @PostMapping("/create")
    @PreAuthorize("!authentication.principal.isBlocked() and hasAnyAuthority('USER','MODERATOR','ADMIN')")
    public ResponseEntity<?> createTask(@RequestBody Task task,
                                        @AuthenticationPrincipal UserPrincipal principal) {


        task.getTestCases().forEach(el -> System.out.println(el.isSample()));
        task.setCreatorUserId(principal.getId());
        Task createdTask = taskService.createTask(task);

        if (task.getTestCases() != null && !task.getTestCases().isEmpty()) {
            for (TestCase tc : task.getTestCases()) {
                tc.setTask(createdTask);
            }
            testCaseService.addAllTests(task.getTestCases());
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("""
        !authentication.principal.blocked and
        (
          hasAuthority('ADMIN')
          or @taskSecurity.isTaskOwner(#id, authentication.principal.id)
        )
   """)
    public TaskDto updateTask(@PathVariable Long id,
                           @RequestBody Task updates,
                           @AuthenticationPrincipal UserPrincipal principal) {
        return taskService.updateTask(id, updates).getDtoObject();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("""
        !authentication.principal.blocked and
        (
          hasAuthority('ADMIN')
          or @taskSecurity.isTaskOwner(#id, authentication.principal.id)
        )
    """)
    public void deleteTask(@PathVariable Long id,
                           @AuthenticationPrincipal UserPrincipal principal) {
        taskService.deleteTask(id);
    }

    /**
     * Пример: GET /api/tasks/search?title=...&categoryId=...&minDifficulty=...&maxDifficulty=...&page=0&size=5
     */
    @GetMapping("/search")
    public List<TaskDto> searchTasks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer minDifficulty,
            @RequestParam(required = false) Integer maxDifficulty,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return taskService.searchTasks(
                title,
                categoryId,
                minDifficulty,
                maxDifficulty,
                page,
                size
        ).stream().map(el -> el.getDtoObject()).toList();
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyAuthority('USER','MODERATOR','ADMIN')")
    public List<TaskDto> getMyTasks(@AuthenticationPrincipal UserPrincipal principal) {
        return taskService.getTasksByCreator(principal.getId()).stream().map(el -> el.getDtoObject()).toList();
    }
}
