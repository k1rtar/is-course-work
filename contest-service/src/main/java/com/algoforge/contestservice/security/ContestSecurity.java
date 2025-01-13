package com.algoforge.contestservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.algoforge.contestservice.repository.ContestRepository;

@Component("contestSecurity")
public class ContestSecurity {

    @Autowired
    private ContestRepository contestRepository;

    public boolean isContestOwner(Long contestId, Long userId) {
        var c = contestRepository.findById(contestId);
        if (c.isEmpty()) return false;
        return c.get().getCreatorUserId() != null
                && c.get().getCreatorUserId().equals(userId);
    }
}
