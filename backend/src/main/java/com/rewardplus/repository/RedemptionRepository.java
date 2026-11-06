package com.rewardplus.repository;

import com.rewardplus.entity.Customer;
import com.rewardplus.entity.Redemption;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RedemptionRepository
        extends JpaRepository<Redemption, Long> {

    List<Redemption> findByCustomer(Customer customer);

    long countByCustomer(Customer customer);

    List<Redemption> findByCustomerOrderByCreatedAtDesc(
        Customer customer,
        Pageable pageable);

}