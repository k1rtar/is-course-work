package com.algoforge.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.algoforge.common.dto.AlgoUserDto;


@FeignClient(name = "auth-service-client", url = "http://auth-service:8081/api/auth")
public interface AuthServiceClient {

    @GetMapping("/users/{username}")
    AlgoUserDto getUserByUsername(@PathVariable("username") String username);


    @PutMapping("/users/{username}/block")
    AlgoUserDto blockUser(@PathVariable("username") String username);


    @PutMapping("/users/{username}/unblock")
    AlgoUserDto unblockUser(@PathVariable("username") String username);

    @DeleteMapping("/users/{username}")
    void deleteUser(@PathVariable("username") String username);
}
