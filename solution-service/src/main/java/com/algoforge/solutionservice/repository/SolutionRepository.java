package com.algoforge.solutionservice.repository;

import com.algoforge.solutionservice.model.Solution;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long> {

    public List<Solution> findByUserId(Long userId);

    Optional<Solution> findByIdAndUserId(Long id, Long userId);

}
