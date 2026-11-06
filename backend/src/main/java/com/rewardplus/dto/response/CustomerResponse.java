package com.rewardplus.dto.response;

import com.rewardplus.entity.MembershipLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomerResponse {

    private Long id;

    private String customerCode;

    private String firstName;

    private String lastName;

    private String phone;

    private MembershipLevel membershipLevel;

    private Integer currentPoints;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}