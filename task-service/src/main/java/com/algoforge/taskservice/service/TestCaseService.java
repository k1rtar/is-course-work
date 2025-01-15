package com.algoforge.taskservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.algoforge.taskservice.model.TestCase;
import com.algoforge.taskservice.repository.TestCaseRepository;
import java.util.List;

@Service
public class TestCaseService {

    @Autowired
    private TestCaseRepository testCaseRepository;

    public List<TestCase> getTestCasesForTask(Long taskId) {
        return testCaseRepository.findByTaskId(taskId);
    }

    public TestCase addTestCase(TestCase tc) {
        return testCaseRepository.save(tc);
    }

    public List<TestCase> addAllTests(List<TestCase> tcList) {
        return testCaseRepository.saveAll(tcList);
    }
}
