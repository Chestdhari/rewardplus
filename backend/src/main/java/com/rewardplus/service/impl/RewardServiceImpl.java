package com.rewardplus.service.impl;

import com.rewardplus.dto.request.CreateRewardRequest;
import com.rewardplus.dto.request.UpdateRewardRequest;
import com.rewardplus.dto.response.RewardResponse;
import com.rewardplus.entity.Reward;
import com.rewardplus.entity.RewardCategory;
import com.rewardplus.exception.RewardCategoryNotFoundException;
import com.rewardplus.exception.RewardNotFoundException;
import com.rewardplus.repository.RewardCategoryRepository;
import com.rewardplus.repository.RewardRepository;
import com.rewardplus.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

    private final RewardRepository rewardRepository;

    private final RewardCategoryRepository rewardCategoryRepository;

    @Override
    public RewardResponse createReward(CreateRewardRequest request) {

        RewardCategory category = rewardCategoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() ->
                        new RewardCategoryNotFoundException(
                                "Reward Category not found"));

        Reward reward = new Reward();

        reward.setRewardName(request.getRewardName());
        reward.setDescription(request.getDescription());
        reward.setCategory(category);
        reward.setPointsRequired(request.getPointsRequired());
        reward.setStockQuantity(request.getStockQuantity());

        if (request.getActive() != null) {
            reward.setActive(request.getActive());
        } else {
            reward.setActive(true);
        }

        Reward savedReward = rewardRepository.save(reward);

        return map(savedReward);
    }

    @Override
    public RewardResponse updateReward(Long id,
                                       UpdateRewardRequest request) {

        Reward reward = rewardRepository.findById(id)
                .orElseThrow(() ->
                        new RewardNotFoundException("Reward not found"));

        RewardCategory category = rewardCategoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() ->
                        new RewardCategoryNotFoundException(
                                "Reward Category not found"));

        reward.setRewardName(request.getRewardName());
        reward.setDescription(request.getDescription());
        reward.setCategory(category);
        reward.setPointsRequired(request.getPointsRequired());
        reward.setStockQuantity(request.getStockQuantity());

        if (request.getActive() != null) {
            reward.setActive(request.getActive());
        }

        Reward updatedReward = rewardRepository.save(reward);

        return map(updatedReward);
    }

    @Override
    public RewardResponse getReward(Long id) {

        Reward reward = rewardRepository.findById(id)
                .orElseThrow(() ->
                        new RewardNotFoundException("Reward not found"));

        return map(reward);
    }

    @Override
    public List<RewardResponse> getAllRewards() {

        return rewardRepository.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<RewardResponse> getActiveRewards() {

        return rewardRepository.findAll()
                .stream()
                .filter(Reward::getActive)
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReward(Long id) {

        Reward reward = rewardRepository.findById(id)
                .orElseThrow(() ->
                        new RewardNotFoundException("Reward not found"));

        rewardRepository.delete(reward);
    }

    private RewardResponse map(Reward reward) {

        RewardResponse response = new RewardResponse();

        response.setId(reward.getId());
        response.setRewardName(reward.getRewardName());
        response.setDescription(reward.getDescription());

        if (reward.getCategory() != null) {
            response.setCategoryName(
                    reward.getCategory().getCategoryName());
        }

        response.setPointsRequired(reward.getPointsRequired());
        response.setStockQuantity(reward.getStockQuantity());
        response.setActive(reward.getActive());
        response.setCreatedAt(reward.getCreatedAt());
        response.setUpdatedAt(reward.getUpdatedAt());

        return response;
    }
}