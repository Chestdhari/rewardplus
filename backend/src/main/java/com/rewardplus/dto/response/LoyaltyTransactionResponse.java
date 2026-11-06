package com.rewardplus.dto.response;

import com.rewardplus.entity.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoyaltyTransactionResponse {

    private Long id;

    private TransactionType transactionType;

    private Integer points;

    private Integer balanceAfterTransaction;

    private String description;

    private String referenceId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}