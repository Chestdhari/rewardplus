package com.rewardplus.controller;

import com.rewardplus.dto.response.CustomerDashboardResponse;
import com.rewardplus.service.CustomerDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer/dashboard")
@RequiredArgsConstructor
public class CustomerDashboardController {

    private final CustomerDashboardService customerDashboardService;

    @GetMapping
    public CustomerDashboardResponse getDashboard() {

        return customerDashboardService.getDashboard();

    }
}