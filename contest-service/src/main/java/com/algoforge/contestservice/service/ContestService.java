package com.algoforge.contestservice.service;

import com.algoforge.contestservice.model.Contest;
import com.algoforge.contestservice.model.ContestParticipant;
import com.algoforge.contestservice.model.ContestParticipantId;
import com.algoforge.contestservice.model.ContestTask;
import com.algoforge.contestservice.repository.ContestParticipantRepository;
import com.algoforge.contestservice.repository.ContestRepository;
import com.algoforge.contestservice.repository.ContestTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ContestService {

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private ContestParticipantRepository participantRepository;

    @Autowired
    private ContestTaskRepository contestTaskRepository;


    public Contest createContestWithOwner(Contest contest, Long creatorUserId) {
        if (contest.getStartTime() == null) {
            contest.setStartTime(LocalDateTime.now().plusDays(1));
        }
        if (contest.getEndTime() == null) {
            contest.setEndTime(contest.getStartTime().plusDays(7));
        }
        if (contest.getStartTime().isAfter(contest.getEndTime())) {
            throw new RuntimeException("StartTime must be before EndTime");
        }
        contest.setCreatorUserId(creatorUserId);
        return contestRepository.save(contest);
    }


    public Contest updateContest(Long contestId, Contest updates) {
        Contest existing = contestRepository.findById(contestId)
                .orElseThrow(() -> new RuntimeException("No contest found with ID=" + contestId));
        existing.setTitle(updates.getTitle());
        existing.setDescription(updates.getDescription());
        existing.setStartTime(updates.getStartTime());
        existing.setEndTime(updates.getEndTime());
        existing.setPublic(updates.isPublic());
        return contestRepository.save(existing);
    }


    public void deleteContest(Long contestId) {
        if (!contestRepository.existsById(contestId)) {
            throw new RuntimeException("Contest not found, cannot delete");
        }
        contestRepository.deleteById(contestId);
    }


    public List<Contest> getAllContests() {
        return contestRepository.findAll();
    }


    public Optional<Contest> getContestById(Long contestId) {
        return contestRepository.findById(contestId);
    }


    public List<Contest> getPublicContests() {
        return contestRepository.findByIsPublicTrue();
    }


    public void addTasksToContest(Long contestId, List<Long> taskIds) {
        for (Long taskId : taskIds) {
            ContestTask ct = new ContestTask();
            ct.setContestId(contestId);
            ct.setTaskId(taskId);
            contestTaskRepository.save(ct);
        }
    }


    public List<ContestTask> getTasksForContest(Long contestId) {
        return contestTaskRepository.findByContestId(contestId);
    }


    public void registerParticipant(Long contestId, Long userId) {
        if (!contestRepository.existsById(contestId)) {
            throw new RuntimeException("Contest does not exist: " + contestId);
        }



        ContestParticipantId cpId = new ContestParticipantId();
        cpId.setContestId(contestId);
        cpId.setUserId(userId);

        if (participantRepository.existsById(cpId)) {
            throw new RuntimeException("User " + userId + " already registered in contest " + contestId);
        }

        ContestParticipant participant = new ContestParticipant();
        participant.setContestId(contestId);
        participant.setUserId(userId);
        participant.setRegistrationDate(LocalDateTime.now());
        participantRepository.save(participant);
    }


    public List<ContestParticipant> getParticipants(Long contestId) {
        return participantRepository.findByContestId(contestId);
    }
}
