package com.rewardplus.service;

import com.rewardplus.dto.request.CreateUserRequest;
import com.rewardplus.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long id, CreateUserRequest request);

    void deleteUser(Long id);

}