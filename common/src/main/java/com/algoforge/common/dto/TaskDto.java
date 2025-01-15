package com.algoforge.common.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import com.algoforge.common.model.TaskDifficulty;

@Data
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private TaskDifficulty difficultyLevel;
    private LocalDateTime creationDate;
    private boolean isPublic;
    private String status;
    private Float maxExecutionTime;
    private Float maxMemoryUsage;
    private Long creatorUserId;
    private List<TestCaseDto> testCases;
}
