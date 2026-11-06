package com.rewardplus.service.impl;

import com.rewardplus.dto.request.CreateCustomerSegmentRequest;
import com.rewardplus.dto.request.UpdateCustomerSegmentRequest;
import com.rewardplus.dto.response.CustomerSegmentResponse;
import com.rewardplus.entity.CustomerSegment;
import com.rewardplus.exception.CustomerSegmentNotFoundException;
import com.rewardplus.repository.CustomerSegmentRepository;
import com.rewardplus.service.CustomerSegmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerSegmentServiceImpl implements CustomerSegmentService {

    private final CustomerSegmentRepository customerSegmentRepository;

    @Override
    public CustomerSegmentResponse create(CreateCustomerSegmentRequest request) {

        CustomerSegment segment = new CustomerSegment();

        segment.setSegmentName(request.getSegmentName());
        segment.setDescription(request.getDescription());

        if (request.getActive() != null) {
            segment.setActive(request.getActive());
        } else {
            segment.setActive(true);
        }

        CustomerSegment savedSegment =
                customerSegmentRepository.save(segment);

        return map(savedSegment);
    }

    @Override
    public CustomerSegmentResponse update(
            Long id,
            UpdateCustomerSegmentRequest request) {

        CustomerSegment segment =
                customerSegmentRepository.findById(id)
                        .orElseThrow(() ->
                                new CustomerSegmentNotFoundException(
                                        "Customer Segment not found"));

        segment.setSegmentName(request.getSegmentName());
        segment.setDescription(request.getDescription());

        if (request.getActive() != null) {
            segment.setActive(request.getActive());
        }

        CustomerSegment updatedSegment =
                customerSegmentRepository.save(segment);

        return map(updatedSegment);
    }
    @Override
    public CustomerSegmentResponse get(Long id) {

        CustomerSegment segment =
                customerSegmentRepository.findById(id)
                        .orElseThrow(() ->
                                new CustomerSegmentNotFoundException(
                                        "Customer Segment not found"));

        return map(segment);
    }

    @Override
    public List<CustomerSegmentResponse> getAll() {

        return customerSegmentRepository.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {

        CustomerSegment segment =
                customerSegmentRepository.findById(id)
                        .orElseThrow(() ->
                                new CustomerSegmentNotFoundException(
                                        "Customer Segment not found"));

        customerSegmentRepository.delete(segment);
    }

    private CustomerSegmentResponse map(CustomerSegment segment) {

        CustomerSegmentResponse response =
                new CustomerSegmentResponse();

        response.setId(segment.getId());
        response.setSegmentName(segment.getSegmentName());
        response.setDescription(segment.getDescription());
        response.setActive(segment.getActive());
        response.setCreatedAt(segment.getCreatedAt());
        response.setUpdatedAt(segment.getUpdatedAt());

        return response;
    }

}