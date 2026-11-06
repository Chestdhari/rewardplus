package com.rewardplus.repository;

import com.rewardplus.entity.RewardCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardCategoryRepository
        extends JpaRepository<RewardCategory,Long> {
}