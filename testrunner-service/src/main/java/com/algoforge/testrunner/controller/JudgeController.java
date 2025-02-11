package com.algoforge.testrunner.controller;

import com.algoforge.common.model.ExecutionRequest;
import com.algoforge.common.model.ExecutionResult;
import com.algoforge.testrunner.service.JudgeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/testrunner")
public class JudgeController {

    @Autowired
    private JudgeService judgeService;

    @PostMapping("/run")
    public ExecutionResult runSolution(@RequestBody ExecutionRequest request) {
        try {
            ExecutionResult result = judgeService.runCode(request);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            ExecutionResult errorResult = new ExecutionResult(false, null, e.getMessage());
            return errorResult;
        }
    }
}

