package com.algoforge.testrunner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algoforge.common.model.ExecutionRequest;
import com.algoforge.common.model.ExecutionResult;
import com.algoforge.common.model.TestCase;
import com.algoforge.common.model.TestResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис, который организует запуск кода в Docker и проверку результатов
 */
@Service
public class JudgeService {

    @Autowired
    private LocalExecutorService localExecutorService;

    public ExecutionResult runCode(ExecutionRequest request) throws Exception {
        List<TestResult> testResults = new ArrayList<>();
        boolean allPassed = true;

        for (TestCase testCase : request.getTestCases()) {
            String executionOutput;
            try {
                executionOutput = localExecutorService.executeCode(
                    request.getLanguage(),
                    request.getCode(),
                    testCase.getInputData(),
                    request.getTimeLimitMillis(),
                    request.getMemoryLimitBytes()
                );
            } catch (Exception e) {
                // В случае ошибки выполнения теста
                executionOutput = e.getMessage();
                allPassed = false;
                testResults.add(new TestResult(
                    testCase.getInputData(),
                    testCase.getExpectedOutput(),
                    executionOutput,
                    false
                ));
                continue;
            }

            // Сравниваем с ожидаемым результатом
            boolean passed = testCase.getExpectedOutput().equals(executionOutput);
            if (!passed) {
                allPassed = false;
            }
            testResults.add(new TestResult(
                testCase.getInputData(),
                testCase.getExpectedOutput(),
                executionOutput,
                passed
            ));
        }

        ExecutionResult result = new ExecutionResult();
        result.setAllPassed(allPassed);
        result.setTestResults(testResults);
        result.setErrorMessage(null);

        return result;
    }
}
