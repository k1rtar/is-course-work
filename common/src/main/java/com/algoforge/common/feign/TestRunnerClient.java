package com.algoforge.common.feign;

import com.algoforge.common.model.ExecutionRequest;
import com.algoforge.common.model.ExecutionResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "test-runner", url = "http://testrunner-service:8090/api/testrunner")
public interface TestRunnerClient {

    @PostMapping("/run")
    public ExecutionResult runSolution(@RequestBody ExecutionRequest request);
}
