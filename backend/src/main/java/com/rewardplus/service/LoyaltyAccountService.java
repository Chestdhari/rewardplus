package com.rewardplus.service;

import com.rewardplus.dto.request.CreateLoyaltyAccountRequest;
import com.rewardplus.dto.request.EarnPointsRequest;
import com.rewardplus.dto.request.RedeemPointsRequest;
import com.rewardplus.dto.response.LoyaltyAccountResponse;
import com.rewardplus.dto.response.LoyaltyTransactionResponse;

import java.util.List;

public interface LoyaltyAccountService {

    LoyaltyAccountResponse createAccount(CreateLoyaltyAccountRequest request);

    LoyaltyAccountResponse getAccount(Long id);

    List<LoyaltyAccountResponse> getAllAccounts();

    /**
     * Returns the loyalty account of the currently logged-in customer.
     */
    LoyaltyAccountResponse getCurrentCustomerLoyalty();

    /**
     * Returns all loyalty point transactions
     * of the currently logged-in customer.
     */
    List<LoyaltyTransactionResponse> getCurrentCustomerTransactions();

    LoyaltyAccountResponse earnPoints(Long accountId,
                                      EarnPointsRequest request);

    LoyaltyAccountResponse redeemPoints(Long accountId,
                                        RedeemPointsRequest request);

}