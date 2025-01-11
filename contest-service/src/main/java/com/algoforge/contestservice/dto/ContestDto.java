package com.algoforge.contestservice.dto;

import lombok.Data;
import java.time.LocalDateTime;


@Data
public class ContestDto {
    private Long contestId;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isPublic;
    private Long creatorUserId;
}
