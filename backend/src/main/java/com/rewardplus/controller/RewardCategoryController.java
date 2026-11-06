package com.rewardplus.controller;

import com.rewardplus.dto.request.CreateRewardCategoryRequest;
import com.rewardplus.dto.request.UpdateRewardCategoryRequest;
import com.rewardplus.dto.response.RewardCategoryResponse;
import com.rewardplus.service.RewardCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reward-categories")
@RequiredArgsConstructor
public class RewardCategoryController {

    private final RewardCategoryService rewardCategoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'MARKETING')")
    public RewardCategoryResponse createCategory(
            @RequestBody CreateRewardCategoryRequest request) {

        return rewardCategoryService.createCategory(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'MARKETING')")
    public RewardCategoryResponse updateCategory(
            @PathVariable Long id,
            @RequestBody UpdateRewardCategoryRequest request) {

        return rewardCategoryService.updateCategory(id, request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'SALES', 'MARKETING', 'CUSTOMER')")
    public RewardCategoryResponse getCategory(
            @PathVariable Long id) {

        return rewardCategoryService.getCategory(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'SALES', 'MARKETING', 'CUSTOMER')")
    public List<RewardCategoryResponse> getAllCategories() {

        return rewardCategoryService.getAllCategories();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'MARKETING')")
    public void deleteCategory(
            @PathVariable Long id) {

        rewardCategoryService.deleteCategory(id);
    }
}