package com.algoforge.common.model;

import java.util.List;

import lombok.Data;

@Data
public class ExecutionResult {
    private boolean allPassed;
    private List<TestResult> testResults; // Можно хранить результаты для каждого теста
    private String errorMessage;      // Если что-то пошло не так (ошибки компиляции/запуска)

    public ExecutionResult() {}

    public ExecutionResult(boolean allPassed, List<TestResult> testResults, String errorMessage) {
        this.allPassed = allPassed;
        this.testResults = testResults;
        this.errorMessage = errorMessage;
    }
}
