package com.algoforge.adminservice.controller;

import com.algoforge.adminservice.service.AdminService;
import com.algoforge.common.auth.UserPrincipal;
import com.algoforge.common.dto.AlgoUserDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @PutMapping("/users/{username}/block")
    public AlgoUserDto blockUser(
            @PathVariable String username,
            @AuthenticationPrincipal UserPrincipal principal) {
        return adminService.blockUser(username);
    }


    @PutMapping("/users/{username}/unblock")
    public AlgoUserDto unblockUser(
            @PathVariable String username,
            @AuthenticationPrincipal UserPrincipal principal) {
        return adminService.unblockUser(username);
    }


    @DeleteMapping("/users/{username}")
    public void deleteUser(
            @PathVariable String username,
            @AuthenticationPrincipal UserPrincipal principal) {
        adminService.deleteUser(username);
    }


    @DeleteMapping("/tasks/{taskId}")
    public void deleteTask(
            @PathVariable Long taskId,
            @AuthenticationPrincipal UserPrincipal principal) {
        adminService.deleteTask(taskId);
    }


    @DeleteMapping("/contests/{contestId}")
    public void deleteContest(
            @PathVariable Long contestId,
            @AuthenticationPrincipal UserPrincipal principal) {
        adminService.deleteContest(contestId);
    }



}
