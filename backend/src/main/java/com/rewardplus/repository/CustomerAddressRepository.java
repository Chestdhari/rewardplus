package com.rewardplus.repository;

import com.rewardplus.entity.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAddressRepository
        extends JpaRepository<CustomerAddress, Long> {
}