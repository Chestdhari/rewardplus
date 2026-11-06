package com.rewardplus.controller;

import com.rewardplus.dto.response.LoyaltyTransactionResponse;
import com.rewardplus.service.LoyaltyAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer/transactions")
@RequiredArgsConstructor
public class CustomerTransactionController {

    private final LoyaltyAccountService loyaltyAccountService;

    @GetMapping
    public List<LoyaltyTransactionResponse> getMyTransactions() {

        return loyaltyAccountService.getCurrentCustomerTransactions();

    }

}