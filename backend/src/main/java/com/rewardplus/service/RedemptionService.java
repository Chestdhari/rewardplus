package com.rewardplus.service;

import com.rewardplus.dto.request.CreateRedemptionRequest;
import com.rewardplus.dto.response.RedemptionResponse;

import java.util.List;

public interface RedemptionService {

    RedemptionResponse redeem(CreateRedemptionRequest request);

    RedemptionResponse getRedemption(Long id);

    List<RedemptionResponse> getAllRedemptions();

    List<RedemptionResponse> getCurrentCustomerRedemptions();

}