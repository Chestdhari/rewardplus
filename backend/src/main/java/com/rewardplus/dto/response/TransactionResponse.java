package com.rewardplus.dto.response;

import com.rewardplus.entity.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TransactionResponse {

    private Long id;

    private String transactionNumber;

    private Long customerId;

    private BigDecimal totalAmount;

    private TransactionStatus status;

    private List<TransactionItemResponse> items;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}