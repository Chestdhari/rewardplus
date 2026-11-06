package com.rewardplus.controller;

import com.rewardplus.dto.request.CreatePromotionRuleRequest;
import com.rewardplus.dto.response.PromotionRuleResponse;
import com.rewardplus.service.PromotionRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotion-rules")
@RequiredArgsConstructor
public class PromotionRuleController {

    private final PromotionRuleService promotionRuleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public PromotionRuleResponse create(
            @RequestBody CreatePromotionRuleRequest request) {

        return promotionRuleService.create(request);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<PromotionRuleResponse> getAll() {

        return promotionRuleService.getAll();
    }

}