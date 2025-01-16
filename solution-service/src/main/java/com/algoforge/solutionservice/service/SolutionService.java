package com.algoforge.solutionservice.service;

import com.algoforge.common.dto.TaskDto;
import com.algoforge.common.dto.TestCaseDto;
import com.algoforge.common.feign.TaskServiceClient;
import com.algoforge.common.feign.TestRunnerClient;
import com.algoforge.common.model.ExecutionRequest;
import com.algoforge.common.model.ExecutionResult;
import com.algoforge.common.model.TestCase;
import com.algoforge.solutionservice.dto.CreateSolutionRequest;
import com.algoforge.solutionservice.model.Solution;
import com.algoforge.solutionservice.model.SolutionStatus;
import com.algoforge.solutionservice.repository.SolutionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SolutionService {

    @Autowired
    private TaskServiceClient taskServiceClient;

    @Autowired
    private TestRunnerClient testRunnerClient;

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public ExecutionResult createAndRunSolution(CreateSolutionRequest request) {
        // 1. Получаем задачу из TaskService
        TaskDto taskDto = taskServiceClient.getTaskById(request.getTaskId());
        if (taskDto == null) {
            throw new RuntimeException("Task not found with ID=" + request.getTaskId());
        }

        // 2. Создаём сущность Solution и сохраняем в БД (со статусом "CREATED")
        Solution solution = new Solution();
        solution.setTaskId(request.getTaskId());
        solution.setUserId(request.getUserId());
        solution.setCode(request.getCode());
        solution.setLanguage(request.getLanguage());
        solution.setStatus(SolutionStatus.CREATED);

        solution = solutionRepository.save(solution);

        // 3. Собираем ExecutionRequest (из common) для отправки в TestRunner
        //    Перекладываем тесты из TaskDto -> TestCase (common)
        List<TestCase> testCaseList = new ArrayList<>();
        for (TestCaseDto tcDto : taskDto.getTestCases()) {
            TestCase t = new TestCase();
            t.setInputData(tcDto.getInputData());
            t.setExpectedOutput(tcDto.getExpectedOutputData());
            testCaseList.add(t);
        }

        // Определяем лимиты
        long timeLimitMillis = 0;
        if (taskDto.getMaxExecutionTime() != null) {
            // например, в секундах храним, нужно перевести в миллисекунды
            timeLimitMillis = (long) (taskDto.getMaxExecutionTime() * 1000);
        }
        long memoryLimitBytes = 0;
        if (taskDto.getMaxMemoryUsage() != null) {
            // например, в мегабайтах, переводим в байты
            memoryLimitBytes = (long) (taskDto.getMaxMemoryUsage() * 1024 * 1024);
        }

        ExecutionRequest execRequest = new ExecutionRequest(
                request.getLanguage(),
                request.getCode(),
                testCaseList,
                timeLimitMillis,
                memoryLimitBytes
        );

        
        // 4. Вызываем TestRunner
        solution.setStatus(SolutionStatus.RUNNING);
        solutionRepository.save(solution);
        System.out.println("starting running");

        ExecutionResult executionResult = testRunnerClient.runSolution(execRequest);
        System.out.println("finished running");
        System.out.println(executionResult.getTestResults());

        // 5. Обновляем решение (статус + возможно сохраняем подробности)
        SolutionStatus newStatus = executionResult.isAllPassed() ? SolutionStatus.ACCEPTED : SolutionStatus.REJECTED;
        solution.setStatus(newStatus);

        if (executionResult.getErrorMessage() != null) {
            solution.setErrorMessage(executionResult.getErrorMessage());
        }

        try {
            String testResultsJson = objectMapper.writeValueAsString(executionResult.getTestResults());
            solution.setTestResultsJson(testResultsJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // Можно установить сообщение об ошибке или обработать иначе
            solution.setErrorMessage("Ошибка при сериализации результатов тестов.");
        }

        solutionRepository.save(solution);

        return executionResult;
    }

    public List<Solution> getSolutionsByUserId(Long userId) {
        return solutionRepository.findByUserId(userId);
    }

    public Solution getSolutionByIdAndUserId(Long solutionId, Long userId) {
        return solutionRepository.findByIdAndUserId(solutionId, userId).orElseThrow();
    }
}
