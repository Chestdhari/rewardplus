package com.rewardplus.service.impl;

import com.rewardplus.dto.request.CreateCustomerRequest;
import com.rewardplus.dto.response.CustomerResponse;
import com.rewardplus.entity.Customer;
import com.rewardplus.entity.User;
import com.rewardplus.exception.CustomerAlreadyExistsException;
import com.rewardplus.exception.CustomerNotFoundException;
import com.rewardplus.exception.PhoneAlreadyExistsException;
import com.rewardplus.exception.UserNotFoundException;
import com.rewardplus.repository.CustomerRepository;
import com.rewardplus.repository.UserRepository;
import com.rewardplus.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest request) {

        if (customerRepository.findByCustomerCode(request.getCustomerCode()).isPresent()) {
            throw new CustomerAlreadyExistsException("Customer code already exists");
        }

        if (customerRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new PhoneAlreadyExistsException("Phone number already exists");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (customerRepository.findByUser(user).isPresent()) {
            throw new CustomerAlreadyExistsException("User already linked with another customer");
        }

        Customer customer = new Customer();

        customer.setCustomerCode(request.getCustomerCode());
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setPhone(request.getPhone());
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setAddress(request.getAddress());
        customer.setCity(request.getCity());
        customer.setState(request.getState());
        customer.setCountry(request.getCountry());
        customer.setPinCode(request.getPinCode());

        if (request.getMembershipLevel() != null) {
            customer.setMembershipLevel(request.getMembershipLevel());
        }

        if (request.getActive() != null) {
            customer.setActive(request.getActive());
        }

        customer.setUser(user);

        Customer savedCustomer = customerRepository.save(customer);

        return mapToResponse(savedCustomer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        return mapToResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomer(Long id, CreateCustomerRequest request) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        if (!customer.getCustomerCode().equals(request.getCustomerCode())
                && customerRepository.findByCustomerCode(request.getCustomerCode()).isPresent()) {
            throw new CustomerAlreadyExistsException("Customer code already exists");
        }

        if (!customer.getPhone().equals(request.getPhone())
                && customerRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new PhoneAlreadyExistsException("Phone number already exists");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        customer.setCustomerCode(request.getCustomerCode());
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setPhone(request.getPhone());
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setAddress(request.getAddress());
        customer.setCity(request.getCity());
        customer.setState(request.getState());
        customer.setCountry(request.getCountry());
        customer.setPinCode(request.getPinCode());

        if (request.getMembershipLevel() != null) {
            customer.setMembershipLevel(request.getMembershipLevel());
        }

        if (request.getActive() != null) {
            customer.setActive(request.getActive());
        }

        customer.setUser(user);

        Customer updatedCustomer = customerRepository.save(customer);

        return mapToResponse(updatedCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        customerRepository.delete(customer);
    }

    private CustomerResponse mapToResponse(Customer customer) {

        CustomerResponse response = new CustomerResponse();

        response.setId(customer.getId());
        response.setCustomerCode(customer.getCustomerCode());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setPhone(customer.getPhone());
        response.setMembershipLevel(customer.getMembershipLevel());
        response.setCurrentPoints(customer.getCurrentPoints());
        response.setActive(customer.getActive());
        response.setCreatedAt(customer.getCreatedAt());
        response.setUpdatedAt(customer.getUpdatedAt());

        return response;
    }
}