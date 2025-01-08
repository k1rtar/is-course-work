package com.algoforge.common.component;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.algoforge.common.auth.AlgoUserDto;

@FeignClient(name = "auth-service", url = "http://auth-service:8081/api/auth/")
public interface UserServiceClient {

    @GetMapping("/users/{username}")
    AlgoUserDto getUserByUsername(@PathVariable("username") String username);


}
