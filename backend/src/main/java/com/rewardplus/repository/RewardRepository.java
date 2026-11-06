package com.rewardplus.repository;

import com.rewardplus.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRepository
        extends JpaRepository<Reward, Long> {

    long countByActiveTrue();

}