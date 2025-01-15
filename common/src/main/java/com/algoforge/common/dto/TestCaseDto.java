package com.algoforge.common.dto;

import lombok.Data;

@Data
public class TestCaseDto {
    private Long testCaseId;
    private String inputData;
    private String expectedOutputData;
    private boolean isSample;
    private Long taskId;
}