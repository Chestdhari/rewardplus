package com.rewardplus.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EarnPointsRequest {

    private Integer points;

    private String description;

    private String referenceId;

}