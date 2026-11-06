package com.rewardplus.service;

import com.rewardplus.dto.request.CreateCustomerAddressRequest;
import com.rewardplus.dto.request.UpdateCustomerAddressRequest;
import com.rewardplus.dto.response.CustomerAddressResponse;

import java.util.List;

public interface CustomerAddressService {

    CustomerAddressResponse create(CreateCustomerAddressRequest request);

    CustomerAddressResponse update(Long id,
                                   UpdateCustomerAddressRequest request);

    CustomerAddressResponse get(Long id);

    List<CustomerAddressResponse> getAll();

    void delete(Long id);

}