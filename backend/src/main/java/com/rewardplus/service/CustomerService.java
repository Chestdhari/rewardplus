package com.rewardplus.service;

import com.rewardplus.dto.request.CreateCustomerRequest;
import com.rewardplus.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {

    CustomerResponse createCustomer(CreateCustomerRequest request);

    List<CustomerResponse> getAllCustomers();

    CustomerResponse getCustomerById(Long id);

    CustomerResponse updateCustomer(Long id,
                                    CreateCustomerRequest request);

    void deleteCustomer(Long id);

}