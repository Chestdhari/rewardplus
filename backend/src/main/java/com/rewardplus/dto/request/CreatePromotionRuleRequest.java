package com.rewardplus.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreatePromotionRuleRequest {

    private Long promotionId;

    private BigDecimal minimumPurchaseAmount;

    private Integer minimumPointsAward;

}