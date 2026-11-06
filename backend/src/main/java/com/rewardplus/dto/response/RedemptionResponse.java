package com.rewardplus.dto.response;

import com.rewardplus.entity.RedemptionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RedemptionResponse {

    private Long id;

    private Long customerId;

    private String rewardName;

    private Integer pointsRedeemed;

    private RedemptionStatus redemptionStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}