package com.algoforge.contestservice.controller;

import com.algoforge.contestservice.model.Contest;
import com.algoforge.contestservice.model.ContestParticipant;
import com.algoforge.contestservice.model.ContestTask;
import com.algoforge.contestservice.service.ContestService;
import com.algoforge.common.auth.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/contests")
public class ContestController {

    @Autowired
    private ContestService contestService;


    @GetMapping("/public")
    public List<Contest> getPublicContests() {
        return contestService.getPublicContests();
    }

    @GetMapping
    public List<Contest> getAllContests() {
        return contestService.getAllContests();
    }


    @GetMapping("/{id}")
    public Contest getContest(@PathVariable Long id) {
        return contestService.getContestById(id)
                .orElseThrow(() -> new RuntimeException("Contest not found with ID=" + id));
    }


    @PostMapping
    public Contest createContest(@RequestBody Contest contest,
                                 @AuthenticationPrincipal UserPrincipal principal) {
        contest.setCreatorUserId(principal.getId());
        return contestService.createContest(contest);
    }


    @PutMapping("/{id}")
    public Contest updateContest(@PathVariable Long id,
                                 @RequestBody Contest updates,
                                 @AuthenticationPrincipal UserPrincipal principal) {
        return contestService.updateContest(id, updates);
    }


    @DeleteMapping("/{id}")
    public void deleteContest(@PathVariable Long id,
                              @AuthenticationPrincipal UserPrincipal principal) {
        contestService.deleteContest(id);
    }


    @PostMapping("/{id}/tasks")
    public void addTasks(@PathVariable Long id,
                         @RequestBody List<Long> taskIds,
                         @AuthenticationPrincipal UserPrincipal principal) {
        contestService.addTasksToContest(id, taskIds);
    }


    @GetMapping("/{id}/tasks")
    public List<ContestTask> getContestTasks(@PathVariable Long id) {
        return contestService.getTasksForContest(id);
    }


    @GetMapping("/{id}/participants")
    public List<ContestParticipant> getParticipants(@PathVariable Long id) {
        return contestService.getParticipants(id);
    }


    @PostMapping("/{id}/participants")
    public void registerParticipant(@PathVariable Long id,
                                    @RequestParam Long userId,
                                    @AuthenticationPrincipal UserPrincipal principal) {
        contestService.registerParticipant(id, userId);
    }
}
