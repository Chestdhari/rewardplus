package com.rewardplus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RewardResponse {

    private Long id;

    private String rewardName;

    private String description;

    private String categoryName;

    private Integer pointsRequired;

    private Integer stockQuantity;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}