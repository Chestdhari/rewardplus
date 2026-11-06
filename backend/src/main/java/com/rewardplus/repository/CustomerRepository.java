package com.rewardplus.repository;

import com.rewardplus.entity.Customer;
import com.rewardplus.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByCustomerCode(String customerCode);

    Optional<Customer> findByPhone(String phone);

    Optional<Customer> findByUser(User user);

    boolean existsByPhone(String phone);

    boolean existsByUser(User user);

}