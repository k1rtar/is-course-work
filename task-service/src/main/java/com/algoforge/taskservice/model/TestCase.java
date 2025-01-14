package com.algoforge.taskservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TestCase")
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TestCaseID")
    private Long testCaseId;

    @Column(name = "InputData", nullable = false)
    private String inputData;

    @Column(name = "ExpectedOutputData", nullable = false)
    private String expectedOutputData;

    @Column(name = "IsSample", nullable = false)
    private boolean isSample;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;
}
