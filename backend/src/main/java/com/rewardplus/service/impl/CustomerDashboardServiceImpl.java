package com.rewardplus.service.impl;

import com.rewardplus.dto.response.CustomerDashboardResponse;
import com.rewardplus.dto.response.LoyaltyTransactionResponse;
import com.rewardplus.dto.response.RedemptionResponse;
import com.rewardplus.entity.*;
import com.rewardplus.exception.CustomerNotFoundException;
import com.rewardplus.exception.LoyaltyAccountNotFoundException;
import com.rewardplus.exception.UserNotFoundException;
import com.rewardplus.repository.*;
import com.rewardplus.service.CustomerDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerDashboardServiceImpl
        implements CustomerDashboardService {

    private final UserRepository userRepository;

    private final LoyaltyAccountRepository loyaltyAccountRepository;

    private final RewardRepository rewardRepository;

    private final LoyaltyPointTransactionRepository transactionRepository;

    private final RedemptionRepository redemptionRepository;

    @Override
    public CustomerDashboardResponse getDashboard() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        Customer customer = user.getCustomer();

        if (customer == null) {
            throw new CustomerNotFoundException(
                    "Customer profile not found");
        }

        LoyaltyAccount loyaltyAccount =
                loyaltyAccountRepository.findByCustomer(customer)
                        .orElseThrow(() ->
                                new LoyaltyAccountNotFoundException(
                                        "Loyalty account not found"));

        CustomerDashboardResponse response =
                new CustomerDashboardResponse();

        response.setCustomerName(
                customer.getFirstName() + " " +
                        customer.getLastName());

        response.setCustomerCode(
                customer.getCustomerCode());

        response.setMembershipLevel(
                loyaltyAccount.getMembershipLevel());

        response.setCurrentPoints(
                loyaltyAccount.getCurrentPoints());

        response.setAvailablePoints(
                loyaltyAccount.getAvailablePoints());

        response.setLifetimePoints(
                loyaltyAccount.getLifetimePoints());

        switch (loyaltyAccount.getMembershipLevel()) {

            case BRONZE:

                response.setNextMembershipLevel("SILVER");

                response.setPointsToNextLevel(
                        1000 - loyaltyAccount.getLifetimePoints());

                break;

            case SILVER:

                response.setNextMembershipLevel("GOLD");

                response.setPointsToNextLevel(
                        5000 - loyaltyAccount.getLifetimePoints());

                break;

            case GOLD:

                response.setNextMembershipLevel("PLATINUM");

                response.setPointsToNextLevel(
                        10000 - loyaltyAccount.getLifetimePoints());

                break;

            case PLATINUM:

                response.setNextMembershipLevel("MAX LEVEL");

                response.setPointsToNextLevel(0);

                break;
        }

        response.setActiveRewards(
                rewardRepository.countByActiveTrue());

        response.setTotalTransactions(
                transactionRepository.countByLoyaltyAccount(
                        loyaltyAccount));

        response.setTotalRedemptions(
                redemptionRepository.countByCustomer(
                        customer));

        List<LoyaltyTransactionResponse> recentTransactions =
                transactionRepository
                        .findByLoyaltyAccountOrderByCreatedAtDesc(
                                loyaltyAccount,
                                PageRequest.of(0, 5))
                        .stream()
                        .map(this::mapTransaction)
                        .collect(Collectors.toList());

        response.setRecentTransactions(
                recentTransactions);

        List<RedemptionResponse> recentRedemptions =
                redemptionRepository
                        .findByCustomerOrderByCreatedAtDesc(
                                customer,
                                PageRequest.of(0, 5))
                        .stream()
                        .map(this::mapRedemption)
                        .collect(Collectors.toList());

        response.setRecentRedemptions(
                recentRedemptions);

        return response;
    }
    private LoyaltyTransactionResponse mapTransaction(
        LoyaltyPointTransaction transaction) {

    LoyaltyTransactionResponse response =
            new LoyaltyTransactionResponse();

    response.setId(transaction.getId());

    response.setTransactionType(
            transaction.getTransactionType());

    response.setPoints(
            transaction.getPoints());

    response.setBalanceAfterTransaction(
            transaction.getBalanceAfterTransaction());

    response.setDescription(
            transaction.getDescription());

    response.setReferenceId(
            transaction.getReferenceId());

    response.setCreatedAt(
            transaction.getCreatedAt());

    response.setUpdatedAt(
            transaction.getUpdatedAt());

    return response;
}

private RedemptionResponse mapRedemption(
        Redemption redemption) {

    RedemptionResponse response =
            new RedemptionResponse();

    response.setId(
            redemption.getId());

    if (redemption.getCustomer() != null) {
        response.setCustomerId(
                redemption.getCustomer().getId());
    }

    if (redemption.getReward() != null) {
        response.setRewardName(
                redemption.getReward().getRewardName());
    }

    response.setPointsRedeemed(
            redemption.getPointsRedeemed());

    response.setRedemptionStatus(
            redemption.getRedemptionStatus());

    response.setCreatedAt(
            redemption.getCreatedAt());

    response.setUpdatedAt(
            redemption.getUpdatedAt());

    return response;
}

}