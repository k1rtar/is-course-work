package com.algoforge.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "contest-service", url = "http://contest-service:8083/api/contests")
public interface ContestServiceClient {

    @DeleteMapping("/{contestId}")
    void deleteContest(@PathVariable("contestId") Long contestId);
}
