package com.algoforge.common.model;

import java.util.List;

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

    public boolean isAllPassed() {
        return allPassed;
    }

    public List<TestResult> getTestResults() {
        return testResults;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setAllPassed(boolean allPassed) {
        this.allPassed = allPassed;
    }

    public void setTestResults(List<TestResult> testResults) {
        this.testResults = testResults;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
