package com.rewardplus.service.impl;

import com.rewardplus.dto.request.CreateRewardCategoryRequest;
import com.rewardplus.dto.request.UpdateRewardCategoryRequest;
import com.rewardplus.dto.response.RewardCategoryResponse;
import com.rewardplus.entity.RewardCategory;
import com.rewardplus.exception.RewardCategoryNotFoundException;
import com.rewardplus.repository.RewardCategoryRepository;
import com.rewardplus.service.RewardCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RewardCategoryServiceImpl implements RewardCategoryService {

    private final RewardCategoryRepository rewardCategoryRepository;

    @Override
    public RewardCategoryResponse createCategory(CreateRewardCategoryRequest request) {

        RewardCategory category = new RewardCategory();

        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());

        if (request.getActive() != null) {
            category.setActive(request.getActive());
        } else {
            category.setActive(true);
        }

        RewardCategory savedCategory =
                rewardCategoryRepository.save(category);

        return map(savedCategory);
    }

    @Override
    public RewardCategoryResponse updateCategory(Long id,
                                                 UpdateRewardCategoryRequest request) {

        RewardCategory category =
                rewardCategoryRepository.findById(id)
                        .orElseThrow(() ->
                                new RewardCategoryNotFoundException(
                                        "Reward Category not found"));

        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());

        if (request.getActive() != null) {
            category.setActive(request.getActive());
        }

        RewardCategory updatedCategory =
                rewardCategoryRepository.save(category);

        return map(updatedCategory);
    }

    @Override
    public RewardCategoryResponse getCategory(Long id) {

        RewardCategory category =
                rewardCategoryRepository.findById(id)
                        .orElseThrow(() ->
                                new RewardCategoryNotFoundException(
                                        "Reward Category not found"));

        return map(category);
    }

    @Override
    public List<RewardCategoryResponse> getAllCategories() {

        return rewardCategoryRepository.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Long id) {

        RewardCategory category =
                rewardCategoryRepository.findById(id)
                        .orElseThrow(() ->
                                new RewardCategoryNotFoundException(
                                        "Reward Category not found"));

        rewardCategoryRepository.delete(category);
    }

    private RewardCategoryResponse map(RewardCategory category) {

        RewardCategoryResponse response =
                new RewardCategoryResponse();

        response.setId(category.getId());
        response.setCategoryName(category.getCategoryName());
        response.setDescription(category.getDescription());
        response.setActive(category.getActive());
        response.setCreatedAt(category.getCreatedAt());
        response.setUpdatedAt(category.getUpdatedAt());

        return response;
    }

}