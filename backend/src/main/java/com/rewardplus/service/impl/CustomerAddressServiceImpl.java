package com.rewardplus.service.impl;

import com.rewardplus.dto.request.CreateCustomerAddressRequest;
import com.rewardplus.dto.request.UpdateCustomerAddressRequest;
import com.rewardplus.dto.response.CustomerAddressResponse;
import com.rewardplus.entity.Customer;
import com.rewardplus.entity.CustomerAddress;
import com.rewardplus.exception.CustomerAddressNotFoundException;
import com.rewardplus.exception.CustomerNotFoundException;
import com.rewardplus.repository.CustomerAddressRepository;
import com.rewardplus.repository.CustomerRepository;
import com.rewardplus.service.CustomerAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerAddressServiceImpl implements CustomerAddressService {

    private final CustomerAddressRepository customerAddressRepository;
    private final CustomerRepository customerRepository;

    @Override
    public CustomerAddressResponse create(CreateCustomerAddressRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found"));

        CustomerAddress address = new CustomerAddress();

        address.setCustomer(customer);
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());

        if (request.getActive() != null) {
            address.setActive(request.getActive());
        } else {
            address.setActive(true);
        }

        CustomerAddress savedAddress =
                customerAddressRepository.save(address);

        return map(savedAddress);
    }

    @Override
    public CustomerAddressResponse update(Long id,
                                          UpdateCustomerAddressRequest request) {

        CustomerAddress address =
                customerAddressRepository.findById(id)
                        .orElseThrow(() ->
                                new CustomerAddressNotFoundException(
                                        "Customer Address not found"));

        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());

        if (request.getActive() != null) {
            address.setActive(request.getActive());
        }

        CustomerAddress updatedAddress =
                customerAddressRepository.save(address);

        return map(updatedAddress);
    }

    @Override
    public CustomerAddressResponse get(Long id) {

        CustomerAddress address =
                customerAddressRepository.findById(id)
                        .orElseThrow(() ->
                                new CustomerAddressNotFoundException(
                                        "Customer Address not found"));

        return map(address);
    }

    @Override
    public List<CustomerAddressResponse> getAll() {

        return customerAddressRepository.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {

        CustomerAddress address =
                customerAddressRepository.findById(id)
                        .orElseThrow(() ->
                                new CustomerAddressNotFoundException(
                                        "Customer Address not found"));

        customerAddressRepository.delete(address);
    }

    private CustomerAddressResponse map(CustomerAddress address) {

        CustomerAddressResponse response =
                new CustomerAddressResponse();

        response.setId(address.getId());
        response.setCustomerId(address.getCustomer().getId());
        response.setAddressLine1(address.getAddressLine1());
        response.setAddressLine2(address.getAddressLine2());
        response.setCity(address.getCity());
        response.setState(address.getState());
        response.setCountry(address.getCountry());
        response.setPostalCode(address.getPostalCode());
        response.setActive(address.getActive());
        response.setCreatedAt(address.getCreatedAt());
        response.setUpdatedAt(address.getUpdatedAt());

        return response;
    }

}