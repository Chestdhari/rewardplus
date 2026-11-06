package com.rewardplus.controller;

import com.rewardplus.dto.request.CreateLoyaltyAccountRequest;
import com.rewardplus.dto.request.EarnPointsRequest;
import com.rewardplus.dto.request.RedeemPointsRequest;
import com.rewardplus.dto.response.LoyaltyAccountResponse;
import com.rewardplus.service.LoyaltyAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loyalty-accounts")
@RequiredArgsConstructor
public class LoyaltyAccountController {

    private final LoyaltyAccountService loyaltyAccountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'SALES')")
    public LoyaltyAccountResponse createAccount(
            @RequestBody CreateLoyaltyAccountRequest request) {

        return loyaltyAccountService.createAccount(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'SALES')")
    public List<LoyaltyAccountResponse> getAllAccounts() {

        return loyaltyAccountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'SALES', 'CUSTOMER')")
    public LoyaltyAccountResponse getAccount(
            @PathVariable Long id) {

        return loyaltyAccountService.getAccount(id);
    }

    @PostMapping("/{id}/earn")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'SALES')")
    public LoyaltyAccountResponse earnPoints(
            @PathVariable Long id,
            @RequestBody EarnPointsRequest request) {

        return loyaltyAccountService.earnPoints(id, request);
    }

    @PostMapping("/{id}/redeem")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'SALES', 'CUSTOMER')")
    public LoyaltyAccountResponse redeemPoints(
            @PathVariable Long id,
            @RequestBody RedeemPointsRequest request) {

        return loyaltyAccountService.redeemPoints(id, request);
    }
}
