package com.algoforge.adminservice.service;

import com.algoforge.common.dto.AlgoUserDto;
import com.algoforge.common.feign.AuthServiceClient;
import com.algoforge.common.feign.ContestServiceClient;
import com.algoforge.common.feign.TaskServiceClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AuthServiceClient authServiceClient;

    @Autowired
    private TaskServiceClient taskServiceClient;

    @Autowired
    private ContestServiceClient contestServiceClient;

    public AlgoUserDto blockUser(String username) {
        return authServiceClient.blockUser(username);
    }

    public AlgoUserDto unblockUser(String username) {
        return authServiceClient.unblockUser(username);
    }

    public void deleteUser(String username) {
        authServiceClient.deleteUser(username);
    }

    public void deleteTask(Long taskId) {
        taskServiceClient.deleteTask(taskId);
    }

    public void deleteContest(Long contestId) {
        contestServiceClient.deleteContest(contestId);
    }

}
