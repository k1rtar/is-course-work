package com.algoforge.contestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algoforge.contestservice.model.ContestParticipant;
import com.algoforge.contestservice.model.ContestParticipantId;

import java.util.List;

@Repository
public interface ContestParticipantRepository
        extends JpaRepository<ContestParticipant, ContestParticipantId> {

    List<ContestParticipant> findByContestId(Long contestId);
    List<ContestParticipant> findByUserId(Long userId);
}
