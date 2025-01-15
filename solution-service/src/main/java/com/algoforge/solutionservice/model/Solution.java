package com.algoforge.solutionservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.algoforge.common.model.Language;

@Data
@Entity
@Table(name = "solutions")
public class Solution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long taskId;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.ORDINAL)
    private SolutionStatus status; // например, "CREATED", "RUNNING", "ACCEPTED", "REJECTED"...

    @Column(nullable = false)
    private Language language;     // язык (python, java, cpp...)

    @Column(columnDefinition = "text")
    private String code;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
