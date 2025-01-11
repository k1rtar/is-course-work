package com.algoforge.contestservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class ContestTaskId implements Serializable {
    private Long contestId;
    private Long taskId;
}
