package com.rewardplus.controller;

import com.rewardplus.dto.request.CreateCustomerSegmentMappingRequest;
import com.rewardplus.dto.response.CustomerSegmentMappingResponse;
import com.rewardplus.service.CustomerSegmentMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-segment-mappings")
@RequiredArgsConstructor
public class CustomerSegmentMappingController {

    private final CustomerSegmentMappingService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public CustomerSegmentMappingResponse create(
            @RequestBody CreateCustomerSegmentMappingRequest request) {

        return service.create(request);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<CustomerSegmentMappingResponse> getAll() {

        return service.getAll();
    }

}