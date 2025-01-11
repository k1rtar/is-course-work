package com.algoforge.contestservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "ContestParticipant")
@IdClass(ContestParticipantId.class)
public class ContestParticipant {

    @Id
    @Column(name = "ContestID")
    private Long contestId;

    @Id
    @Column(name = "UserID")
    private Long userId;

    @Column(name = "RegistrationDate")
    private LocalDateTime registrationDate = LocalDateTime.now();
}
