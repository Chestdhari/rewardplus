package com.rewardplus.service;

import com.rewardplus.dto.request.CreateRoleRequest;
import com.rewardplus.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {

    RoleResponse createRole(CreateRoleRequest request);

    List<RoleResponse> getAllRoles();

    RoleResponse getRoleById(Long id);

    RoleResponse updateRole(Long id, CreateRoleRequest request);

    void deleteRole(Long id);
}