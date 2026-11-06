package com.rewardplus.exception;

public class RewardNotFoundException extends RuntimeException {

    public RewardNotFoundException(String message) {
        super(message);
    }

}