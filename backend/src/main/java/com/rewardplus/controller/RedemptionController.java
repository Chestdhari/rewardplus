package com.rewardplus.controller;

import com.rewardplus.dto.request.CreateRedemptionRequest;
import com.rewardplus.dto.response.RedemptionResponse;
import com.rewardplus.service.RedemptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/redemptions")
@RequiredArgsConstructor
public class RedemptionController {

    private final RedemptionService redemptionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'SALES')")
    public RedemptionResponse redeem(
            @RequestBody CreateRedemptionRequest request) {

        return redemptionService.redeem(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'SALES')")
    public RedemptionResponse getRedemption(
            @PathVariable Long id) {

        return redemptionService.getRedemption(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'SALES')")
    public List<RedemptionResponse> getAllRedemptions() {

        return redemptionService.getAllRedemptions();
    }

}