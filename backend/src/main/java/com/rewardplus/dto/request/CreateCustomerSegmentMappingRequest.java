package com.rewardplus.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCustomerSegmentMappingRequest {

    private Long customerId;

    private Long segmentId;

}