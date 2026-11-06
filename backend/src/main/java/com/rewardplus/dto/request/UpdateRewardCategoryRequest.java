package com.rewardplus.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRewardCategoryRequest {

    private String categoryName;

    private String description;

    private Boolean active;

}