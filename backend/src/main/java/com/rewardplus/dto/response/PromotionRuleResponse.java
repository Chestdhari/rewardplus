package com.rewardplus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PromotionRuleResponse {

    private Long id;

    private Long promotionId;

    private String promotionName;

    private BigDecimal minimumPurchaseAmount;

    private Integer minimumPointsAward;

    private LocalDateTime createdAt;

}