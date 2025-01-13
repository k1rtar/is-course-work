package com.algoforge.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.algoforge.common.auth.AlgoUserDto;

@FeignClient(name = "user-service-client", url = "http://auth-service:8081/api/auth/")
public interface UserServiceClient {

    @GetMapping("/users/{username}")
    AlgoUserDto getUserByUsername(@PathVariable("username") String username);


}
