package com.rewardplus.controller;

import com.rewardplus.dto.request.CreateCustomerRequest;
import com.rewardplus.dto.response.CustomerResponse;
import com.rewardplus.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'SALES')")
    public CustomerResponse createCustomer(
            @RequestBody CreateCustomerRequest request) {

        return customerService.createCustomer(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'SALES')")
    public List<CustomerResponse> getAllCustomers() {

        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'SALES')")
    public CustomerResponse getCustomerById(
            @PathVariable Long id) {

        return customerService.getCustomerById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'SALES')")
    public CustomerResponse updateCustomer(
            @PathVariable Long id,
            @RequestBody CreateCustomerRequest request) {

        return customerService.updateCustomer(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteCustomer(
            @PathVariable Long id) {

        customerService.deleteCustomer(id);
    }
}