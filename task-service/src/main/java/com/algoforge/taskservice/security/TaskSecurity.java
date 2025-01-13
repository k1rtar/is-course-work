package com.algoforge.taskservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.algoforge.taskservice.repository.TaskRepository;

@Component("taskSecurity")
public class TaskSecurity {

    @Autowired
    private TaskRepository taskRepository;

    public boolean isTaskOwner(Long taskId, Long userId) {
        var taskOpt = taskRepository.findById(taskId);
        if (taskOpt.isEmpty()) return false;
        return taskOpt.get().getCreatorUserId() != null
                && taskOpt.get().getCreatorUserId().equals(userId);
    }
}
