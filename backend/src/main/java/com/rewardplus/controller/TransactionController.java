package com.rewardplus.controller;

import com.rewardplus.dto.request.CreateTransactionRequest;
import com.rewardplus.dto.response.TransactionResponse;
import com.rewardplus.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'SALES')")
    public TransactionResponse createTransaction(
            @RequestBody CreateTransactionRequest request) {

        return transactionService.createTransaction(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'SALES')")
    public TransactionResponse getTransaction(
            @PathVariable Long id) {

        return transactionService.getTransaction(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'SALES')")
    public List<TransactionResponse> getAllTransactions() {

        return transactionService.getAllTransactions();
    }

}