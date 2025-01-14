package com.algoforge.common.model;

import java.util.List;

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

    public Language getLanguage() {
        return language;
    }

    public String getCode() {
        return code;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public long getTimeLimitMillis() {
        return timeLimitMillis;
    }

    public long getMemoryLimitBytes() {
        return memoryLimitBytes;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTestCases(List<TestCase> testCases) {
        this.testCases = testCases;
    }

    public void setTimeLimitMillis(long timeLimitMillis) {
        this.timeLimitMillis = timeLimitMillis;
    }

    public void setMemoryLimitBytes(long memoryLimitBytes) {
        this.memoryLimitBytes = memoryLimitBytes;
    }
}
