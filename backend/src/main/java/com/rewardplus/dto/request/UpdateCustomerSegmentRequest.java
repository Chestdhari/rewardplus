package com.rewardplus.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCustomerSegmentRequest {

    private String segmentName;

    private String description;

    private Boolean active;

}