package com.algoforge.taskservice.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.algoforge.taskservice.model.TestCase;
import com.algoforge.taskservice.service.TestCaseService;
import java.util.List;

@RestController
@RequestMapping("/api/tasks/{taskId}/testcases")
public class TestCaseController {

    @Autowired
    private TestCaseService testCaseService;

    @GetMapping
    public List<TestCase> getTestCases(@PathVariable Long taskId) {
        return testCaseService.getTestCasesForTask(taskId);
    }

    @PostMapping
    public TestCase createTestCase(@PathVariable Long taskId,
                                   @RequestBody TestCase tc) {
        tc.setTaskId(taskId);
        return testCaseService.addTestCase(tc);
    }
}
