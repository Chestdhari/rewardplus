package com.rewardplus.service.impl;

import com.rewardplus.dto.request.CreatePromotionRuleRequest;
import com.rewardplus.dto.response.PromotionRuleResponse;
import com.rewardplus.entity.Promotion;
import com.rewardplus.entity.PromotionRule;
import com.rewardplus.exception.PromotionNotFoundException;
import com.rewardplus.repository.PromotionRepository;
import com.rewardplus.repository.PromotionRuleRepository;
import com.rewardplus.service.PromotionRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionRuleServiceImpl implements PromotionRuleService {

    private final PromotionRepository promotionRepository;
    private final PromotionRuleRepository promotionRuleRepository;

    @Override
    public PromotionRuleResponse create(CreatePromotionRuleRequest request) {

        Promotion promotion = promotionRepository.findById(request.getPromotionId())
                .orElseThrow(() ->
                        new PromotionNotFoundException("Promotion not found"));

        PromotionRule rule = new PromotionRule();

        rule.setPromotion(promotion);
        rule.setMinimumPurchaseAmount(request.getMinimumPurchaseAmount());
        rule.setMinimumPointsAward(request.getMinimumPointsAward());

        PromotionRule saved = promotionRuleRepository.save(rule);

        return map(saved);
    }

    @Override
    public List<PromotionRuleResponse> getAll() {

        return promotionRuleRepository.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private PromotionRuleResponse map(PromotionRule rule) {

        PromotionRuleResponse response =
                new PromotionRuleResponse();

        response.setId(rule.getId());
        response.setPromotionId(rule.getPromotion().getId());
        response.setPromotionName(rule.getPromotion().getPromotionName());
        response.setMinimumPurchaseAmount(rule.getMinimumPurchaseAmount());
        response.setMinimumPointsAward(rule.getMinimumPointsAward());
        response.setCreatedAt(rule.getCreatedAt());

        return response;
    }

}