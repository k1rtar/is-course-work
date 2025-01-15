package com.algoforge.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.algoforge.common.auth.FeignConfig;
import com.algoforge.common.dto.TaskDto;

@FeignClient(name = "task-service", url = "http://task-service:8082/api/tasks", configuration = FeignConfig.class)
public interface TaskServiceClient {

    @DeleteMapping("/{taskId}")
    void deleteTask(@PathVariable("taskId") Long taskId);

    @GetMapping("/{taskId}")
    TaskDto getTaskById(@PathVariable("taskId") Long taskId);

}
