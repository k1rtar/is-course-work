package com.algoforge.authservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthStubController {

    @GetMapping("/hello")
    public String helloAuth() {
        return "Hello from hello 123";
    }
}
