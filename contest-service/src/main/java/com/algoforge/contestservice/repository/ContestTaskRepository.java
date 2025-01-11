package com.algoforge.contestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algoforge.contestservice.model.ContestTask;
import com.algoforge.contestservice.model.ContestTaskId;

import java.util.List;

@Repository
public interface ContestTaskRepository
        extends JpaRepository<ContestTask, ContestTaskId> {

    List<ContestTask> findByContestId(Long contestId);
}
