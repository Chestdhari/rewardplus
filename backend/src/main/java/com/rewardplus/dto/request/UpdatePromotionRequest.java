package com.rewardplus.dto.request;

import com.rewardplus.entity.PromotionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdatePromotionRequest {

    private String promotionName;

    private String description;

    private PromotionType promotionType;

    private Double bonusMultiplier;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean active;

}