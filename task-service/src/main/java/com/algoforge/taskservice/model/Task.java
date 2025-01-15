package com.algoforge.taskservice.model;

import com.algoforge.common.dto.TaskDto;
import com.algoforge.common.model.TaskDifficulty;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@Table(name = "Task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TaskID")
    private Long id;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Description", nullable = false)
    private String description;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "DifficultyLevel", nullable = false)
    private TaskDifficulty difficultyLevel;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @JsonProperty("isPublic")
    @Column(name = "IsPublic")
    private boolean isPublic;

    @Column(name = "Status")
    private String status;

    @Column(name = "MaxExecutionTime")
    private Float maxExecutionTime;

    @Column(name = "MaxMemoryUsage")
    private Float maxMemoryUsage;

    @Column(name = "CreatorUserID")
    private Long creatorUserId;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TestCase> testCases = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "task_category",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();

    public TaskDto getDtoObject() {

        TaskDto dto = new TaskDto();
        dto.setId(id);
        dto.setTitle(title);
        dto.setDescription(description);
        dto.setDifficultyLevel(difficultyLevel);
        dto.setCreationDate(creationDate);
        dto.setPublic(isPublic);
        dto.setStatus(status);
        dto.setMaxExecutionTime(maxExecutionTime);
        dto.setMaxMemoryUsage(maxMemoryUsage);
        dto.setCreatorUserId(creatorUserId);
        dto.setTestCases(testCases.stream().map(el -> el.getDtoObject()).toList());

        return dto;

    }
}
