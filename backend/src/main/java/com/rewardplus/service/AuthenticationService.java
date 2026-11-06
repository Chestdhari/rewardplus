package com.rewardplus.service;

import com.rewardplus.dto.request.CustomerRegistrationRequest;
import com.rewardplus.dto.request.LoginRequest;
import com.rewardplus.dto.response.CustomerRegistrationResponse;
import com.rewardplus.dto.response.LoginResponse;

public interface AuthenticationService {

    LoginResponse login(LoginRequest request);

    CustomerRegistrationResponse registerCustomer(CustomerRegistrationRequest request);

}