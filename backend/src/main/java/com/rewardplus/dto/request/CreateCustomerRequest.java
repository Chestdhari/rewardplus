package com.rewardplus.dto.request;

import com.rewardplus.entity.MembershipLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateCustomerRequest {

    private String customerCode;

    private String firstName;

    private String lastName;

    private String phone;

    private LocalDate dateOfBirth;

    private String address;

    private String city;

    private String state;

    private String country;

    private String pinCode;

    private MembershipLevel membershipLevel;

    private Boolean active;

    private Long userId;

}