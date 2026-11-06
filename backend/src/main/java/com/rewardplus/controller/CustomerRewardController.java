package com.rewardplus.controller;

import com.rewardplus.dto.response.RewardResponse;
import com.rewardplus.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer/rewards")
@RequiredArgsConstructor
public class CustomerRewardController {

    private final RewardService rewardService;

    @GetMapping
    public List<RewardResponse> getActiveRewards() {

        return rewardService.getActiveRewards();

    }
}