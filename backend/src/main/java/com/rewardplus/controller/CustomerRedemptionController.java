package com.rewardplus.controller;

import com.rewardplus.dto.request.CreateRedemptionRequest;
import com.rewardplus.dto.request.CustomerRedeemRequest;
import com.rewardplus.dto.response.RedemptionResponse;
import com.rewardplus.entity.Customer;
import com.rewardplus.repository.UserRepository;
import com.rewardplus.service.RedemptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer/redemptions")
@RequiredArgsConstructor
public class CustomerRedemptionController {

    private final RedemptionService redemptionService;

    private final UserRepository userRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RedemptionResponse redeem(
            @RequestBody CustomerRedeemRequest request) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        var user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Customer customer = user.getCustomer();

        if (customer == null) {
            throw new RuntimeException(
                    "Customer profile not found");
        }

        CreateRedemptionRequest create =
                new CreateRedemptionRequest();

        create.setCustomerId(customer.getId());

        create.setRewardId(request.getRewardId());

        return redemptionService.redeem(create);
    }

    @GetMapping
    public List<RedemptionResponse> getMyRedemptions() {

        return redemptionService.getCurrentCustomerRedemptions();

    }
}