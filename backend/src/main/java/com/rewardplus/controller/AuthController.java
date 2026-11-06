package com.rewardplus.controller;

import com.rewardplus.dto.request.CustomerRegistrationRequest;
import com.rewardplus.dto.request.LoginRequest;
import com.rewardplus.dto.response.CustomerRegistrationResponse;
import com.rewardplus.dto.response.LoginResponse;
import com.rewardplus.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authenticationService.login(request);
    }

    @PostMapping("/register")
    public CustomerRegistrationResponse registerCustomer(
            @Valid @RequestBody CustomerRegistrationRequest request) {

        return authenticationService.registerCustomer(request);
    }
}