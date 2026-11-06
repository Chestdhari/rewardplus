package com.rewardplus.service;

import com.rewardplus.dto.request.CreateRewardRequest;
import com.rewardplus.dto.request.UpdateRewardRequest;
import com.rewardplus.dto.response.RewardResponse;

import java.util.List;

public interface RewardService {

    RewardResponse createReward(CreateRewardRequest request);

    RewardResponse updateReward(Long id,
                                UpdateRewardRequest request);

    RewardResponse getReward(Long id);

    List<RewardResponse> getAllRewards();

    /**
     * Returns only active rewards for customers.
     */
    List<RewardResponse> getActiveRewards();

    void deleteReward(Long id);

}