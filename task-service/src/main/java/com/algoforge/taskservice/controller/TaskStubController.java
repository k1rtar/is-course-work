package com.algoforge.taskservice.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algoforge.common.auth.UserPrincipal;

@RestController
@RequestMapping("/api/tasks")
public class TaskStubController {

    @GetMapping("/hello")
    public void helloTask(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        System.out.println(userPrincipal.getEmail());
    }
}
