package com.rewardplus.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterCustomerResponse {

    private Long customerId;

    private String customerCode;

    private String username;

    private String email;

    private String message;

}