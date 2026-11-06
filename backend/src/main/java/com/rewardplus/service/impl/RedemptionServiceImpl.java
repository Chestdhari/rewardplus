package com.rewardplus.service.impl;

import com.rewardplus.dto.request.CreateRedemptionRequest;
import com.rewardplus.dto.response.RedemptionResponse;
import com.rewardplus.entity.*;
import com.rewardplus.exception.CustomerNotFoundException;
import com.rewardplus.exception.InsufficientPointsException;
import com.rewardplus.exception.LoyaltyAccountNotFoundException;
import com.rewardplus.exception.RedemptionNotFoundException;
import com.rewardplus.exception.RewardNotFoundException;
import com.rewardplus.exception.UserNotFoundException;
import com.rewardplus.repository.CustomerRepository;
import com.rewardplus.repository.LoyaltyAccountRepository;
import com.rewardplus.repository.LoyaltyPointTransactionRepository;
import com.rewardplus.repository.RedemptionRepository;
import com.rewardplus.repository.RewardRepository;
import com.rewardplus.repository.UserRepository;
import com.rewardplus.service.RedemptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RedemptionServiceImpl implements RedemptionService {

    private final RedemptionRepository redemptionRepository;

    private final CustomerRepository customerRepository;

    private final RewardRepository rewardRepository;

    private final LoyaltyAccountRepository loyaltyAccountRepository;

    private final LoyaltyPointTransactionRepository pointTransactionRepository;

    private final UserRepository userRepository;

    @Override
    public RedemptionResponse redeem(CreateRedemptionRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found"));

        Reward reward = rewardRepository.findById(request.getRewardId())
                .orElseThrow(() ->
                        new RewardNotFoundException("Reward not found"));

        LoyaltyAccount loyaltyAccount =
                loyaltyAccountRepository.findByCustomer(customer)
                        .orElseThrow(() ->
                                new LoyaltyAccountNotFoundException(
                                        "Loyalty account not found"));

        if (loyaltyAccount.getAvailablePoints() <
                reward.getPointsRequired()) {

            throw new InsufficientPointsException(
                    "Insufficient loyalty points");
        }

        if (reward.getStockQuantity() <= 0) {

            throw new RuntimeException(
                    "Reward is out of stock");
        }

        loyaltyAccount.setCurrentPoints(
                loyaltyAccount.getCurrentPoints()
                        - reward.getPointsRequired());

        loyaltyAccount.setAvailablePoints(
                loyaltyAccount.getAvailablePoints()
                        - reward.getPointsRequired());

        reward.setStockQuantity(
                reward.getStockQuantity() - 1);

        Redemption redemption = new Redemption();

        redemption.setCustomer(customer);

        redemption.setReward(reward);

        redemption.setPointsRedeemed(
                reward.getPointsRequired());

        redemption.setRedemptionStatus(
                RedemptionStatus.COMPLETED);

        Redemption savedRedemption =
                redemptionRepository.save(redemption);

        loyaltyAccountRepository.save(loyaltyAccount);

        rewardRepository.save(reward);

        LoyaltyPointTransaction pointTransaction =
                new LoyaltyPointTransaction();

        pointTransaction.setLoyaltyAccount(loyaltyAccount);

        pointTransaction.setTransactionType(
                TransactionType.REDEEM);

        pointTransaction.setPoints(
                reward.getPointsRequired());

        pointTransaction.setBalanceAfterTransaction(
                loyaltyAccount.getCurrentPoints());

        pointTransaction.setDescription(
                "Reward Redeemed");

        pointTransaction.setReferenceId(
                "RED-" + savedRedemption.getId());

        pointTransactionRepository.save(pointTransaction);

        return map(savedRedemption);
    }

    @Override
    public RedemptionResponse getRedemption(Long id) {

        Redemption redemption =
                redemptionRepository.findById(id)
                        .orElseThrow(() ->
                                new RedemptionNotFoundException(
                                        "Redemption not found"));

        return map(redemption);
    }

    @Override
    public List<RedemptionResponse> getAllRedemptions() {

        return redemptionRepository.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<RedemptionResponse> getCurrentCustomerRedemptions() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        Customer customer = user.getCustomer();

        if (customer == null) {
            throw new CustomerNotFoundException(
                    "Customer profile not found");
        }

        return redemptionRepository.findByCustomer(customer)
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private RedemptionResponse map(Redemption redemption) {

        RedemptionResponse response =
                new RedemptionResponse();

        response.setId(redemption.getId());

        response.setCustomerId(
                redemption.getCustomer().getId());

        response.setRewardName(
                redemption.getReward().getRewardName());

        response.setPointsRedeemed(
                redemption.getPointsRedeemed());

        response.setRedemptionStatus(
                redemption.getRedemptionStatus());

        response.setCreatedAt(
                redemption.getCreatedAt());

        response.setUpdatedAt(
                redemption.getUpdatedAt());

        return response;
    }

}