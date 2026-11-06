package com.rewardplus.service.impl;

import com.rewardplus.dto.request.CreateLoyaltyAccountRequest;
import com.rewardplus.dto.request.EarnPointsRequest;
import com.rewardplus.dto.request.RedeemPointsRequest;
import com.rewardplus.dto.response.LoyaltyAccountResponse;
import com.rewardplus.dto.response.LoyaltyTransactionResponse;
import com.rewardplus.entity.Customer;
import com.rewardplus.entity.LoyaltyAccount;
import com.rewardplus.entity.LoyaltyPointTransaction;
import com.rewardplus.entity.MembershipLevel;
import com.rewardplus.entity.TransactionType;
import com.rewardplus.entity.User;
import com.rewardplus.exception.CustomerNotFoundException;
import com.rewardplus.exception.InsufficientPointsException;
import com.rewardplus.exception.LoyaltyAccountNotFoundException;
import com.rewardplus.exception.UserNotFoundException;
import com.rewardplus.repository.CustomerRepository;
import com.rewardplus.repository.LoyaltyAccountRepository;
import com.rewardplus.repository.LoyaltyPointTransactionRepository;
import com.rewardplus.repository.UserRepository;
import com.rewardplus.service.LoyaltyAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoyaltyAccountServiceImpl implements LoyaltyAccountService {

    private final LoyaltyAccountRepository loyaltyAccountRepository;

    private final LoyaltyPointTransactionRepository transactionRepository;

    private final CustomerRepository customerRepository;

    private final UserRepository userRepository;

    @Override
    public LoyaltyAccountResponse createAccount(CreateLoyaltyAccountRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found"));

        LoyaltyAccount account = new LoyaltyAccount();

        account.setCustomer(customer);
        account.setCurrentPoints(0);
        account.setAvailablePoints(0);
        account.setLifetimePoints(0);
        account.setMembershipLevel(MembershipLevel.BRONZE);
        account.setActive(true);

        LoyaltyAccount savedAccount = loyaltyAccountRepository.save(account);

        return map(savedAccount);
    }

    @Override
    public LoyaltyAccountResponse getAccount(Long id) {

        LoyaltyAccount account = loyaltyAccountRepository.findById(id)
                .orElseThrow(() ->
                        new LoyaltyAccountNotFoundException("Loyalty Account not found"));

        return map(account);
    }

    @Override
    public List<LoyaltyAccountResponse> getAllAccounts() {

        return loyaltyAccountRepository.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public LoyaltyAccountResponse getCurrentCustomerLoyalty() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        Customer customer = user.getCustomer();

        if (customer == null) {
            throw new CustomerNotFoundException("Customer profile not found");
        }

        LoyaltyAccount account = loyaltyAccountRepository
                .findByCustomer(customer)
                .orElseThrow(() ->
                        new LoyaltyAccountNotFoundException(
                                "Loyalty account not found"));

        return map(account);
    }

    @Override
    public List<LoyaltyTransactionResponse> getCurrentCustomerTransactions() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        Customer customer = user.getCustomer();

        if (customer == null) {
            throw new CustomerNotFoundException("Customer profile not found");
        }

        LoyaltyAccount account = loyaltyAccountRepository
                .findByCustomer(customer)
                .orElseThrow(() ->
                        new LoyaltyAccountNotFoundException(
                                "Loyalty account not found"));

        return transactionRepository.findByLoyaltyAccount(account)
                .stream()
                .map(this::mapTransaction)
                .collect(Collectors.toList());
    }

    @Override
    public LoyaltyAccountResponse earnPoints(Long accountId,
                                             EarnPointsRequest request) {

        LoyaltyAccount account = loyaltyAccountRepository.findById(accountId)
                .orElseThrow(() ->
                        new LoyaltyAccountNotFoundException("Loyalty Account not found"));

        account.setCurrentPoints(
                account.getCurrentPoints() + request.getPoints());

        account.setAvailablePoints(
                account.getAvailablePoints() + request.getPoints());

        account.setLifetimePoints(
                account.getLifetimePoints() + request.getPoints());

        updateMembership(account);

        LoyaltyPointTransaction transaction = new LoyaltyPointTransaction();

        transaction.setLoyaltyAccount(account);
        transaction.setTransactionType(TransactionType.EARN);
        transaction.setPoints(request.getPoints());
        transaction.setBalanceAfterTransaction(account.getCurrentPoints());
        transaction.setDescription(request.getDescription());
        transaction.setReferenceId(request.getReferenceId());

        transactionRepository.save(transaction);

        LoyaltyAccount updatedAccount =
                loyaltyAccountRepository.save(account);

        return map(updatedAccount);
    }

    @Override
    public LoyaltyAccountResponse redeemPoints(Long accountId,
                                               RedeemPointsRequest request) {

        LoyaltyAccount account = loyaltyAccountRepository.findById(accountId)
                .orElseThrow(() ->
                        new LoyaltyAccountNotFoundException("Loyalty Account not found"));

        if (account.getAvailablePoints() < request.getPoints()) {
            throw new InsufficientPointsException(
                    "Insufficient loyalty points");
        }

        account.setCurrentPoints(
                account.getCurrentPoints() - request.getPoints());

        account.setAvailablePoints(
                account.getAvailablePoints() - request.getPoints());

        LoyaltyPointTransaction transaction = new LoyaltyPointTransaction();

        transaction.setLoyaltyAccount(account);
        transaction.setTransactionType(TransactionType.REDEEM);
        transaction.setPoints(request.getPoints());
        transaction.setBalanceAfterTransaction(account.getCurrentPoints());
        transaction.setDescription(request.getDescription());
        transaction.setReferenceId(request.getReferenceId());

        transactionRepository.save(transaction);

        LoyaltyAccount updatedAccount =
                loyaltyAccountRepository.save(account);

        return map(updatedAccount);
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

    private LoyaltyAccountResponse map(LoyaltyAccount account) {

        LoyaltyAccountResponse response = new LoyaltyAccountResponse();

        response.setId(account.getId());

        if (account.getCustomer() != null) {
            response.setCustomerId(account.getCustomer().getId());
        }

        response.setCurrentPoints(account.getCurrentPoints());
        response.setAvailablePoints(account.getAvailablePoints());
        response.setLifetimePoints(account.getLifetimePoints());
        response.setMembershipLevel(account.getMembershipLevel());
        response.setActive(account.getActive());
        response.setCreatedAt(account.getCreatedAt());
        response.setUpdatedAt(account.getUpdatedAt());

        return response;
    }

    private LoyaltyTransactionResponse mapTransaction(
            LoyaltyPointTransaction transaction) {

        LoyaltyTransactionResponse response =
                new LoyaltyTransactionResponse();

        response.setId(transaction.getId());
        response.setTransactionType(transaction.getTransactionType());
        response.setPoints(transaction.getPoints());
        response.setBalanceAfterTransaction(
                transaction.getBalanceAfterTransaction());
        response.setDescription(transaction.getDescription());
        response.setReferenceId(transaction.getReferenceId());
        response.setCreatedAt(transaction.getCreatedAt());
        response.setUpdatedAt(transaction.getUpdatedAt());

        return response;
    }

}