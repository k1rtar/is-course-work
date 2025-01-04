package com.algoforge.testrunnerservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test-runner")
public class TestRunnerStubController {

    @GetMapping("/hello")
    public String helloTestRunner() {
        return "Hello from Test Runner Service Stub";
    }
}
