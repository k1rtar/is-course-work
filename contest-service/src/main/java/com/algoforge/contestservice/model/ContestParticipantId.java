package com.algoforge.contestservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class ContestParticipantId implements Serializable {
    private Long contestId;
    private Long userId;
}
