package com.rewardplus.controller;

import com.rewardplus.dto.request.CreateCustomerSegmentRequest;
import com.rewardplus.dto.request.UpdateCustomerSegmentRequest;
import com.rewardplus.dto.response.CustomerSegmentResponse;
import com.rewardplus.service.CustomerSegmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-segments")
@RequiredArgsConstructor
public class CustomerSegmentController {

    private final CustomerSegmentService customerSegmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'MARKETING')")
    public CustomerSegmentResponse create(
            @RequestBody CreateCustomerSegmentRequest request) {

        return customerSegmentService.create(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'MARKETING')")
    public CustomerSegmentResponse update(
            @PathVariable Long id,
            @RequestBody UpdateCustomerSegmentRequest request) {

        return customerSegmentService.update(id, request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'MARKETING')")
    public CustomerSegmentResponse get(
            @PathVariable Long id) {

        return customerSegmentService.get(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'MARKETING')")
    public List<CustomerSegmentResponse> getAll() {

        return customerSegmentService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'MARKETING')")
    public void delete(
            @PathVariable Long id) {

        customerSegmentService.delete(id);
    }

}