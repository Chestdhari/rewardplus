package com.rewardplus.service;

import com.rewardplus.dto.request.CreateCustomerSegmentRequest;
import com.rewardplus.dto.request.UpdateCustomerSegmentRequest;
import com.rewardplus.dto.response.CustomerSegmentResponse;

import java.util.List;

public interface CustomerSegmentService {

    CustomerSegmentResponse create(CreateCustomerSegmentRequest request);

    CustomerSegmentResponse update(Long id,
                                   UpdateCustomerSegmentRequest request);

    CustomerSegmentResponse get(Long id);

    List<CustomerSegmentResponse> getAll();

    void delete(Long id);

}