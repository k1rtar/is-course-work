package com.algoforge.common.model;

import lombok.Data;

@Data
public class TestResult {
    private String inputData;
    private String expectedOutput;
    private String actualOutput;
    private boolean passed;

    public TestResult() {}

    public TestResult(String inputData, String expectedOutput, String actualOutput, boolean passed) {
        this.inputData = inputData;
        this.expectedOutput = expectedOutput;
        this.actualOutput = actualOutput;
        this.passed = passed;
    }
}
