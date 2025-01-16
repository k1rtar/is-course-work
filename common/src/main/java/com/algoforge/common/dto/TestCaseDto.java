package com.algoforge.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TestCaseDto {
    private Long testCaseId;
    private String inputData;
    private String expectedOutputData;
    @JsonProperty("isSample")
    private boolean isSample;
    private Long taskId;
}