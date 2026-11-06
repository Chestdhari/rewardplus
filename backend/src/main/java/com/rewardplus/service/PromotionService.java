package com.rewardplus.service;

import com.rewardplus.dto.request.CreatePromotionRequest;
import com.rewardplus.dto.request.UpdatePromotionRequest;
import com.rewardplus.dto.response.PromotionResponse;

import java.util.List;

public interface PromotionService {

    PromotionResponse createPromotion(CreatePromotionRequest request);

    PromotionResponse updatePromotion(Long id,
                                      UpdatePromotionRequest request);

    PromotionResponse getPromotion(Long id);

    List<PromotionResponse> getAllPromotions();

    void deletePromotion(Long id);

}