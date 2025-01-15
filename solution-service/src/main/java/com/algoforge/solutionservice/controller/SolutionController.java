package com.algoforge.solutionservice.controller;

import com.algoforge.common.auth.UserPrincipal;
import com.algoforge.common.model.ExecutionResult;
import com.algoforge.solutionservice.dto.CreateSolutionRequest;
import com.algoforge.solutionservice.service.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
