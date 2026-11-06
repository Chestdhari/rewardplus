package com.rewardplus.dto.response;

import com.rewardplus.entity.MembershipLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerDashboardResponse {

    private String customerName;

    private String customerCode;

    private MembershipLevel membershipLevel;

    private Integer currentPoints;

    private Integer availablePoints;

    private Integer lifetimePoints;

    private String nextMembershipLevel;

    private Integer pointsToNextLevel;

    private Long activeRewards;

    private Long totalTransactions;

    private Long totalRedemptions;

    private List<LoyaltyTransactionResponse> recentTransactions;

    private List<RedemptionResponse> recentRedemptions;

}