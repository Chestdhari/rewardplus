package com.rewardplus.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateTransactionRequest {

    private Long customerId;

    private List<TransactionItemRequest> items;

}