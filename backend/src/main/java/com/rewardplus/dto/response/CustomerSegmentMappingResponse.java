package com.rewardplus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomerSegmentMappingResponse {

    private Long id;

    private Long customerId;

    private String customerName;

    private Long segmentId;

    private String segmentName;

    private LocalDateTime createdAt;

}