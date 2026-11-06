package com.rewardplus.service.impl;

import com.rewardplus.dto.request.CreateCustomerSegmentMappingRequest;
import com.rewardplus.dto.response.CustomerSegmentMappingResponse;
import com.rewardplus.entity.Customer;
import com.rewardplus.entity.CustomerSegment;
import com.rewardplus.entity.CustomerSegmentMapping;
import com.rewardplus.exception.CustomerNotFoundException;
import com.rewardplus.exception.CustomerSegmentNotFoundException;
import com.rewardplus.repository.CustomerRepository;
import com.rewardplus.repository.CustomerSegmentMappingRepository;
import com.rewardplus.repository.CustomerSegmentRepository;
import com.rewardplus.service.CustomerSegmentMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerSegmentMappingServiceImpl
        implements CustomerSegmentMappingService {

    private final CustomerRepository customerRepository;

    private final CustomerSegmentRepository customerSegmentRepository;

    private final CustomerSegmentMappingRepository mappingRepository;

    @Override
    public CustomerSegmentMappingResponse create(
            CreateCustomerSegmentMappingRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found"));

        CustomerSegment segment = customerSegmentRepository.findById(request.getSegmentId())
                .orElseThrow(() ->
                        new CustomerSegmentNotFoundException("Segment not found"));

        CustomerSegmentMapping mapping = new CustomerSegmentMapping();

        mapping.setCustomer(customer);
        mapping.setCustomerSegment(segment);

        CustomerSegmentMapping saved = mappingRepository.save(mapping);

        return map(saved);
    }

    @Override
    public List<CustomerSegmentMappingResponse> getAll() {

        return mappingRepository.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private CustomerSegmentMappingResponse map(CustomerSegmentMapping mapping) {

        CustomerSegmentMappingResponse response =
                new CustomerSegmentMappingResponse();

        response.setId(mapping.getId());
        response.setCustomerId(mapping.getCustomer().getId());
        response.setCustomerName(
                mapping.getCustomer().getFirstName() + " " +
                mapping.getCustomer().getLastName());

        response.setSegmentId(mapping.getCustomerSegment().getId());
        response.setSegmentName(
                mapping.getCustomerSegment().getSegmentName());

        response.setCreatedAt(mapping.getCreatedAt());

        return response;
    }

}