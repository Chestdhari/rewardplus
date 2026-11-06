package com.rewardplus.service.impl;

import com.rewardplus.dto.request.CreatePromotionRequest;
import com.rewardplus.dto.request.UpdatePromotionRequest;
import com.rewardplus.dto.response.PromotionResponse;
import com.rewardplus.entity.Promotion;
import com.rewardplus.exception.PromotionNotFoundException;
import com.rewardplus.repository.PromotionRepository;
import com.rewardplus.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    @Override
    public PromotionResponse createPromotion(CreatePromotionRequest request) {

        Promotion promotion = new Promotion();

        promotion.setPromotionName(request.getPromotionName());
        promotion.setDescription(request.getDescription());
        promotion.setPromotionType(request.getPromotionType());
        promotion.setBonusMultiplier(request.getBonusMultiplier());
        promotion.setStartDate(request.getStartDate());
        promotion.setEndDate(request.getEndDate());

        if (request.getActive() != null) {
            promotion.setActive(request.getActive());
        } else {
            promotion.setActive(true);
        }

        Promotion savedPromotion =
                promotionRepository.save(promotion);

        return map(savedPromotion);
    }

    @Override
    public PromotionResponse updatePromotion(Long id,
                                             UpdatePromotionRequest request) {

        Promotion promotion =
                promotionRepository.findById(id)
                        .orElseThrow(() ->
                                new PromotionNotFoundException(
                                        "Promotion not found"));

        promotion.setPromotionName(request.getPromotionName());
        promotion.setDescription(request.getDescription());
        promotion.setPromotionType(request.getPromotionType());
        promotion.setBonusMultiplier(request.getBonusMultiplier());
        promotion.setStartDate(request.getStartDate());
        promotion.setEndDate(request.getEndDate());

        if (request.getActive() != null) {
            promotion.setActive(request.getActive());
        }

        Promotion updatedPromotion =
                promotionRepository.save(promotion);

        return map(updatedPromotion);
    }
    @Override
    public PromotionResponse getPromotion(Long id) {

        Promotion promotion =
                promotionRepository.findById(id)
                        .orElseThrow(() ->
                                new PromotionNotFoundException(
                                        "Promotion not found"));

        return map(promotion);
    }

    @Override
    public List<PromotionResponse> getAllPromotions() {

        return promotionRepository.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePromotion(Long id) {

        Promotion promotion =
                promotionRepository.findById(id)
                        .orElseThrow(() ->
                                new PromotionNotFoundException(
                                        "Promotion not found"));

        promotionRepository.delete(promotion);
    }

    private PromotionResponse map(Promotion promotion) {

        PromotionResponse response =
                new PromotionResponse();

        response.setId(promotion.getId());
        response.setPromotionName(promotion.getPromotionName());
        response.setDescription(promotion.getDescription());
        response.setPromotionType(promotion.getPromotionType());
        response.setBonusMultiplier(promotion.getBonusMultiplier());
        response.setStartDate(promotion.getStartDate());
        response.setEndDate(promotion.getEndDate());
        response.setActive(promotion.getActive());
        response.setCreatedAt(promotion.getCreatedAt());
        response.setUpdatedAt(promotion.getUpdatedAt());

        return response;
    }

}