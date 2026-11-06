package com.rewardplus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomerAddressResponse {

    private Long id;

    private Long customerId;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String state;

    private String country;

    private String postalCode;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}