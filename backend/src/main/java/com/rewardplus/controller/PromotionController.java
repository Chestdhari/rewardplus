package com.rewardplus.controller;

import com.rewardplus.dto.request.CreatePromotionRequest;
import com.rewardplus.dto.request.UpdatePromotionRequest;
import com.rewardplus.dto.response.PromotionResponse;
import com.rewardplus.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'MARKETING')")
    public PromotionResponse createPromotion(
            @RequestBody CreatePromotionRequest request) {

        return promotionService.createPromotion(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'MARKETING')")
    public PromotionResponse updatePromotion(
            @PathVariable Long id,
            @RequestBody UpdatePromotionRequest request) {

        return promotionService.updatePromotion(id, request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'SALES', 'MARKETING', 'CUSTOMER')")
    public PromotionResponse getPromotion(
            @PathVariable Long id) {

        return promotionService.getPromotion(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'SALES', 'MARKETING', 'CUSTOMER')")
    public List<PromotionResponse> getAllPromotions() {

        return promotionService.getAllPromotions();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'MARKETING')")
    public void deletePromotion(
            @PathVariable Long id) {

        promotionService.deletePromotion(id);
    }
}