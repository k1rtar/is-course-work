package com.algoforge.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "task-service", url = "http://task-service:8082/api/tasks")
public interface TaskServiceClient {

    @DeleteMapping("/{taskId}")
    void deleteTask(@PathVariable("taskId") Long taskId);

}
