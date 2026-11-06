package com.rewardplus.service.impl;

import com.rewardplus.dto.request.CreateRoleRequest;
import com.rewardplus.dto.response.RoleResponse;
import com.rewardplus.entity.Role;
import com.rewardplus.exception.RoleAlreadyExistsException;
import com.rewardplus.exception.RoleNotFoundException;
import com.rewardplus.repository.RoleRepository;
import com.rewardplus.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleResponse createRole(CreateRoleRequest request) {

        if (roleRepository.findByRoleName(request.getRoleName()).isPresent()) {
            throw new RoleAlreadyExistsException("Role already exists");
        }

        Role role = new Role();
        role.setRoleName(request.getRoleName());
        role.setDescription(request.getDescription());
        role.setActive(request.getActive());

        Role savedRole = roleRepository.save(role);

        return mapToResponse(savedRole);
    }

    @Override
    public List<RoleResponse> getAllRoles() {

        return roleRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RoleResponse getRoleById(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        return mapToResponse(role);
    }

    @Override
    public RoleResponse updateRole(Long id, CreateRoleRequest request) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        role.setRoleName(request.getRoleName());
        role.setDescription(request.getDescription());
        role.setActive(request.getActive());

        Role updatedRole = roleRepository.save(role);

        return mapToResponse(updatedRole);
    }

    @Override
    public void deleteRole(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        roleRepository.delete(role);
    }

    /**
     * Converts Role Entity to RoleResponse DTO
     */
    private RoleResponse mapToResponse(Role role) {

        RoleResponse response = new RoleResponse();

        response.setId(role.getId());
        response.setRoleName(role.getRoleName());
        response.setDescription(role.getDescription());
        response.setActive(role.getActive());
        response.setCreatedAt(role.getCreatedAt());
        response.setUpdatedAt(role.getUpdatedAt());

        return response;
    }
}