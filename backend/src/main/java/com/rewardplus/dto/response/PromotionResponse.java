package com.rewardplus.dto.response;

import com.rewardplus.entity.PromotionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PromotionResponse {

    private Long id;

    private String promotionName;

    private String description;

    private PromotionType promotionType;

    private Double bonusMultiplier;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}