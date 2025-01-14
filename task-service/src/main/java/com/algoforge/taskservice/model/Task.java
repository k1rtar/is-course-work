package com.algoforge.taskservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "Task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TaskID")
    private Long taskId;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "DifficultyLevel", nullable = false)
    private int difficultyLevel;

    @Column(name = "CreationDate")
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

    /**
     * Многие-к-многим через таблицу TaskCategory.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "TaskCategory",
            joinColumns = @JoinColumn(name = "TaskID"),
            inverseJoinColumns = @JoinColumn(name = "CategoryID")
    )
    private Set<Category> categories = new HashSet<>();
}
