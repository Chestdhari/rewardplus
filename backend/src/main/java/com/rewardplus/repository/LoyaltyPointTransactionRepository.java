package com.rewardplus.repository;

import com.rewardplus.entity.LoyaltyAccount;
import com.rewardplus.entity.LoyaltyPointTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface LoyaltyPointTransactionRepository
        extends JpaRepository<LoyaltyPointTransaction, Long> {

    List<LoyaltyPointTransaction> findByLoyaltyAccount(LoyaltyAccount loyaltyAccount);

    long countByLoyaltyAccount(LoyaltyAccount loyaltyAccount);

    List<LoyaltyPointTransaction> findByLoyaltyAccountOrderByCreatedAtDesc(
        LoyaltyAccount loyaltyAccount,
        Pageable pageable);

}