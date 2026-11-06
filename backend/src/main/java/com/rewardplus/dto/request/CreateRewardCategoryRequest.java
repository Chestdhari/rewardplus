package com.rewardplus.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRewardCategoryRequest {

    private String categoryName;

    private String description;

    private Boolean active;

}