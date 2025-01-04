package com.algoforge.taskservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskStubController {

    @GetMapping("/hello")
    public String helloTask() {
        return "Hello from Task Service Stub";
    }
}
