package com.algoforge.taskservice.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.algoforge.common.auth.UserPrincipal;
import com.algoforge.taskservice.model.Task;
import com.algoforge.taskservice.model.TestCase;
import com.algoforge.taskservice.service.TaskService;
import com.algoforge.taskservice.service.TestCaseService;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tasks/{taskId}/testcases")
public class TestCaseController {

    @Autowired
    private TestCaseService testCaseService;
    
    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<TestCase> getTestCases(@PathVariable Long taskId) {
        return testCaseService.getTestCasesForTask(taskId);
    }

    @PostMapping
    public ResponseEntity<?> createTestCase(@PathVariable Long taskId,
            @RequestBody @Valid List<TestCase> testCases,
            @AuthenticationPrincipal UserPrincipal principal) {
        
        Task task = taskService.findById(taskId)
                        .orElseThrow(() -> new RuntimeException("No Task found with ID=" + taskId));
        System.out.println(task.getTitle());
        testCases.forEach((test) -> test.setTask(task));

        testCaseService.addAllTests(testCases);

        return ResponseEntity.ok().build();
    }
}
