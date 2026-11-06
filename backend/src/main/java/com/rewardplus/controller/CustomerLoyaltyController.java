package com.rewardplus.controller;

import com.rewardplus.dto.response.LoyaltyAccountResponse;
import com.rewardplus.service.LoyaltyAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer/loyalty")
@RequiredArgsConstructor
public class CustomerLoyaltyController {

    private final LoyaltyAccountService loyaltyAccountService;

    @GetMapping
    public LoyaltyAccountResponse getMyLoyaltyAccount() {

        return loyaltyAccountService.getCurrentCustomerLoyalty();

    }
}