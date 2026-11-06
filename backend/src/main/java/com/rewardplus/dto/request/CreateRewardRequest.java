package com.rewardplus.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRewardRequest {

    private String rewardName;

    private String description;

    private Long categoryId;

    private Integer pointsRequired;

    private Integer stockQuantity;

    private Boolean active;

}