package com.rewardplus.controller;

import com.rewardplus.dto.request.CreateRewardRequest;
import com.rewardplus.dto.request.UpdateRewardRequest;
import com.rewardplus.dto.response.RewardResponse;
import com.rewardplus.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
public class RewardController {

    private final RewardService rewardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'MARKETING')")
    public RewardResponse createReward(
            @RequestBody CreateRewardRequest request) {

        return rewardService.createReward(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'MARKETING')")
    public RewardResponse updateReward(
            @PathVariable Long id,
            @RequestBody UpdateRewardRequest request) {

        return rewardService.updateReward(id, request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'SALES', 'MARKETING')")
    public RewardResponse getReward(
            @PathVariable Long id) {

        return rewardService.getReward(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'SALES', 'MARKETING')")
    public List<RewardResponse> getAllRewards() {

        return rewardService.getAllRewards();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'MARKETING')")
    public void deleteReward(
            @PathVariable Long id) {

        rewardService.deleteReward(id);
    }

}