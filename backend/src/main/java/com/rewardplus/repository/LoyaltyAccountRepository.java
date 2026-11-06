package com.rewardplus.repository;

import com.rewardplus.entity.Customer;
import com.rewardplus.entity.LoyaltyAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoyaltyAccountRepository
        extends JpaRepository<LoyaltyAccount, Long> {

    Optional<LoyaltyAccount> findByCustomer(Customer customer);

}