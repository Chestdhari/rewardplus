package com.rewardplus.service;

import com.rewardplus.dto.request.CreateTransactionRequest;
import com.rewardplus.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

    TransactionResponse createTransaction(CreateTransactionRequest request);

    TransactionResponse getTransaction(Long id);

    List<TransactionResponse> getAllTransactions();

}