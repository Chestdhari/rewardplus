package com.rewardplus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponse {

    private Long id;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private Boolean active;

    private String roleName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}