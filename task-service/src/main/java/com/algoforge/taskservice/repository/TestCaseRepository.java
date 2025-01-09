package com.algoforge.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.algoforge.taskservice.model.TestCase;
import java.util.List;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {

    List<TestCase> findByTaskId(Long taskId);
}
