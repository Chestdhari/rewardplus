package com.rewardplus.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionItemRequest {

    private String productName;

    private Integer quantity;

    private BigDecimal unitPrice;

}