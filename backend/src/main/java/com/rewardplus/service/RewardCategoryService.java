package com.rewardplus.service;

import com.rewardplus.dto.request.CreateRewardCategoryRequest;
import com.rewardplus.dto.request.UpdateRewardCategoryRequest;
import com.rewardplus.dto.response.RewardCategoryResponse;

import java.util.List;

public interface RewardCategoryService {

    RewardCategoryResponse createCategory(CreateRewardCategoryRequest request);

    RewardCategoryResponse updateCategory(Long id,
                                          UpdateRewardCategoryRequest request);

    RewardCategoryResponse getCategory(Long id);

    List<RewardCategoryResponse> getAllCategories();

    void deleteCategory(Long id);

}