package com.algoforge.solutionservice.dto;

import com.algoforge.common.model.Language;

import lombok.Data;

@Data
public class CreateSolutionRequest {
    private Long taskId;
    private Long userId;
    private Language language;
    private String code;
}
