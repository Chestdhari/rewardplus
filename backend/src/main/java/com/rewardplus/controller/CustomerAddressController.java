package com.rewardplus.controller;

import com.rewardplus.dto.request.CreateCustomerAddressRequest;
import com.rewardplus.dto.request.UpdateCustomerAddressRequest;
import com.rewardplus.dto.response.CustomerAddressResponse;
import com.rewardplus.service.CustomerAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-addresses")
@RequiredArgsConstructor
public class CustomerAddressController {

    private final CustomerAddressService customerAddressService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public CustomerAddressResponse create(
            @RequestBody CreateCustomerAddressRequest request) {

        return customerAddressService.create(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CustomerAddressResponse update(
            @PathVariable Long id,
            @RequestBody UpdateCustomerAddressRequest request) {

        return customerAddressService.update(id, request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CustomerAddressResponse get(
            @PathVariable Long id) {

        return customerAddressService.get(id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<CustomerAddressResponse> getAll() {

        return customerAddressService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(
            @PathVariable Long id) {

        customerAddressService.delete(id);
    }

}