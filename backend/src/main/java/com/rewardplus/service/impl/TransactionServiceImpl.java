package com.rewardplus.service.impl;

import com.rewardplus.dto.request.CreateTransactionRequest;
import com.rewardplus.dto.request.TransactionItemRequest;
import com.rewardplus.dto.response.TransactionItemResponse;
import com.rewardplus.dto.response.TransactionResponse;
import com.rewardplus.entity.*;
import com.rewardplus.exception.CustomerNotFoundException;
import com.rewardplus.exception.LoyaltyAccountNotFoundException;
import com.rewardplus.exception.TransactionNotFoundException;
import com.rewardplus.repository.CustomerRepository;
import com.rewardplus.repository.LoyaltyAccountRepository;
import com.rewardplus.repository.LoyaltyPointTransactionRepository;
import com.rewardplus.repository.TransactionRepository;
import com.rewardplus.service.TransactionService;
import com.rewardplus.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final LoyaltyAccountRepository loyaltyAccountRepository;
    private final LoyaltyPointTransactionRepository pointTransactionRepository;
    private final AuditLogService auditLogService;

    @Override
    public TransactionResponse createTransaction(CreateTransactionRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found"));

        LoyaltyAccount loyaltyAccount =
                loyaltyAccountRepository.findByCustomer(customer)
                        .orElseThrow(() ->
                                new LoyaltyAccountNotFoundException(
                                        "Loyalty Account not found"));

        Transaction transaction = new Transaction();

        transaction.setCustomer(customer);

        transaction.setTransactionNumber(
                "TXN" + System.currentTimeMillis());

        transaction.setStatus(TransactionStatus.COMPLETED);

        List<TransactionItem> items = new ArrayList<>();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (TransactionItemRequest itemRequest : request.getItems()) {

            TransactionItem item = new TransactionItem();

            item.setTransaction(transaction);

            item.setProductName(itemRequest.getProductName());

            item.setQuantity(itemRequest.getQuantity());

            item.setUnitPrice(itemRequest.getUnitPrice());

            BigDecimal totalPrice =
                    itemRequest.getUnitPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            itemRequest.getQuantity()));

            item.setTotalPrice(totalPrice);

            totalAmount = totalAmount.add(totalPrice);

            items.add(item);
        }

        transaction.setItems(items);

        transaction.setTotalAmount(totalAmount);

        Transaction savedTransaction =
                transactionRepository.save(transaction);

        int earnedPoints =
                totalAmount.divide(BigDecimal.valueOf(100)).intValue();

        loyaltyAccount.setCurrentPoints(
                loyaltyAccount.getCurrentPoints() + earnedPoints);

        loyaltyAccount.setAvailablePoints(
                loyaltyAccount.getAvailablePoints() + earnedPoints);

        loyaltyAccount.setLifetimePoints(
                loyaltyAccount.getLifetimePoints() + earnedPoints);

        updateMembership(loyaltyAccount);

        LoyaltyPointTransaction pointTransaction =
                new LoyaltyPointTransaction();

        pointTransaction.setLoyaltyAccount(loyaltyAccount);

        pointTransaction.setTransactionType(TransactionType.EARN);

        pointTransaction.setPoints(earnedPoints);

        pointTransaction.setBalanceAfterTransaction(
                loyaltyAccount.getCurrentPoints());

        pointTransaction.setDescription(
                "Points earned from transaction");

        pointTransaction.setReferenceId(
                savedTransaction.getTransactionNumber());

        pointTransactionRepository.save(pointTransaction);

        loyaltyAccountRepository.save(loyaltyAccount);

        String operator = "SYSTEM";
        try {
            org.springframework.security.core.Authentication auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                operator = auth.getName();
            }
        } catch (Exception e) {
            // Ignore
        }
        auditLogService.log(operator, "CHECKOUT", "Transaction", savedTransaction.getId(), 
                "Completed POS checkout transaction #" + savedTransaction.getTransactionNumber() + 
                " for customer: " + customer.getFirstName() + " " + customer.getLastName() + 
                " (Earned: " + earnedPoints + " pts)");

        return map(savedTransaction);
    }
    @Override
    public TransactionResponse getTransaction(Long id) {

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() ->
                        new TransactionNotFoundException("Transaction not found"));

        return map(transaction);
    }

    @Override
    public List<TransactionResponse> getAllTransactions() {

        return transactionRepository.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private void updateMembership(LoyaltyAccount account) {

        int lifetimePoints = account.getLifetimePoints();

        if (lifetimePoints >= 10000) {
            account.setMembershipLevel(MembershipLevel.PLATINUM);

        } else if (lifetimePoints >= 5000) {
            account.setMembershipLevel(MembershipLevel.GOLD);

        } else if (lifetimePoints >= 1000) {
            account.setMembershipLevel(MembershipLevel.SILVER);

        } else {
            account.setMembershipLevel(MembershipLevel.BRONZE);
        }
    }

    private TransactionResponse map(Transaction transaction) {

        TransactionResponse response = new TransactionResponse();

        response.setId(transaction.getId());

        response.setTransactionNumber(
                transaction.getTransactionNumber());

        response.setCustomerId(
                transaction.getCustomer().getId());

        response.setTotalAmount(
                transaction.getTotalAmount());

        response.setStatus(
                transaction.getStatus());

        response.setItems(
                transaction.getItems()
                        .stream()
                        .map(this::mapItem)
                        .collect(Collectors.toList()));

        response.setCreatedAt(
                transaction.getCreatedAt());

        response.setUpdatedAt(
                transaction.getUpdatedAt());

        return response;
    }

    private TransactionItemResponse mapItem(TransactionItem item) {

        TransactionItemResponse response =
                new TransactionItemResponse();

        response.setId(item.getId());

        response.setProductName(item.getProductName());

        response.setQuantity(item.getQuantity());

        response.setUnitPrice(item.getUnitPrice());

        response.setTotalPrice(item.getTotalPrice());

        return response;
    }

}