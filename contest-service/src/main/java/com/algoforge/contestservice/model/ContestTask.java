package com.algoforge.contestservice.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "ContestTask")
@IdClass(ContestTaskId.class)
public class ContestTask {

    @Id
    @Column(name = "ContestID")
    private Long contestId;

    @Id
    @Column(name = "TaskID")
    private Long taskId;
}
