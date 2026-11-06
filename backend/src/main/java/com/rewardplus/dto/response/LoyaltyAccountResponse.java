package com.rewardplus.dto.response;

import com.rewardplus.entity.MembershipLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoyaltyAccountResponse {

    private Long id;

    private Long customerId;

    private Integer currentPoints;

    private Integer availablePoints;

    private Integer lifetimePoints;

    private MembershipLevel membershipLevel;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}