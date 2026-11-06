package com.rewardplus.service;

import com.rewardplus.dto.request.CreateCustomerSegmentMappingRequest;
import com.rewardplus.dto.response.CustomerSegmentMappingResponse;

import java.util.List;

public interface CustomerSegmentMappingService {

    CustomerSegmentMappingResponse create(CreateCustomerSegmentMappingRequest request);

    List<CustomerSegmentMappingResponse> getAll();

}