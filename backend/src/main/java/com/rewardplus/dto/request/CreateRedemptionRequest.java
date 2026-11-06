package com.rewardplus.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRedemptionRequest {

    private Long customerId;

    private Long rewardId;

}

//this one is for the admin 
