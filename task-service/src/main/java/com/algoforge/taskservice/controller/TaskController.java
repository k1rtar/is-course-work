package com.algoforge.taskservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.algoforge.common.auth.UserPrincipal;
import com.algoforge.taskservice.model.Task;
import com.algoforge.taskservice.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;


    @GetMapping("/public")
    public List<Task> listPublicTasks() {
        return taskService.getAllPublicTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.findById(id)
                .orElseThrow(() -> new RuntimeException("No Task found with ID=" + id));
    }

    @PostMapping
    @PreAuthorize("!authentication.principal.blocked and hasAnyAuthority('USER','MODERATOR','ADMIN')")
    public Task createTask(@RequestBody Task task,
                           @AuthenticationPrincipal UserPrincipal principal) {
        if (principal.isBlocked()) {
            throw new RuntimeException("User is blocked, cannot create task.");
        }
        System.out.println(principal.getId());
        System.out.println(principal.toString());
        task.setCreatorUserId(principal.getId());
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    @PreAuthorize("""
        !authentication.principal.blocked and
        (
          hasAuthority('ADMIN')
          or @taskSecurity.isTaskOwner(#id, authentication.principal.id)
        )
   """)
    public Task updateTask(@PathVariable Long id,
                           @RequestBody Task updates,
                           @AuthenticationPrincipal UserPrincipal principal) {
        return taskService.updateTask(id, updates);
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
}
