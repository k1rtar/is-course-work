package com.algoforge.common.model;

import java.util.List;

import lombok.Data;


@Data
public class ExecutionRequest {
    private Language language;   // e.g. "python", "java", "cpp"
    private String code;       // исходный код решения
    private List<TestCase> testCases; // список тестов
    private long timeLimitMillis;     // ограничение по времени
    private long memoryLimitBytes;    // ограничение по памяти

    public ExecutionRequest() {}

    public ExecutionRequest(Language language, String code, List<TestCase> testCases,
                            long timeLimitMillis, long memoryLimitBytes) {
        this.language = language;
        this.code = code;
        this.testCases = testCases;
        this.timeLimitMillis = timeLimitMillis;
        this.memoryLimitBytes = memoryLimitBytes;
    }
}
