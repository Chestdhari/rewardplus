package com.rewardplus.service;

import com.rewardplus.dto.request.CreatePromotionRuleRequest;
import com.rewardplus.dto.response.PromotionRuleResponse;

import java.util.List;

public interface PromotionRuleService {

    PromotionRuleResponse create(CreatePromotionRuleRequest request);

    List<PromotionRuleResponse> getAll();

}