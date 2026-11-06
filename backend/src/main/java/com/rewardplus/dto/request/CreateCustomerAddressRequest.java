package com.rewardplus.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCustomerAddressRequest {

    private Long customerId;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String state;

    private String country;

    private String postalCode;

    private Boolean active;

}