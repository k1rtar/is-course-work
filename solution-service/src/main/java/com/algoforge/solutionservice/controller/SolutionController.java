package com.algoforge.solutionservice.controller;

import com.algoforge.common.auth.UserPrincipal;
import com.algoforge.common.model.ExecutionResult;
import com.algoforge.solutionservice.dto.CreateSolutionRequest;
import com.algoforge.solutionservice.model.Solution;
import com.algoforge.solutionservice.service.SolutionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/solutions")
public class SolutionController {

    @Autowired
    private SolutionService solutionService;

    /**
     * Принимает решение от пользователя, 
     * вызывает TaskService за тестами, 
     * отправляет на TestRunner, 
     * сохраняет в БД,
     * возвращает результат.
     */
    @PostMapping("/submit")
    public ResponseEntity<ExecutionResult> submitSolution(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody CreateSolutionRequest request) {
        try {
            request.setUserId(userPrincipal.getId());
            ExecutionResult result = solutionService.createAndRunSolution(request);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Solution>> getUserSolutions(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            List<Solution> solutions = solutionService.getSolutionsByUserId(userPrincipal.getId());
            return ResponseEntity.ok(solutions);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solution> getSolutionById(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long id) {
        try {
            Solution solution = solutionService.getSolutionByIdAndUserId(id, userPrincipal.getId());
            if (solution != null) {
                return ResponseEntity.ok(solution);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
