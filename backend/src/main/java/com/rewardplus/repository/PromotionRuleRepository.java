package com.rewardplus.repository;

import com.rewardplus.entity.PromotionRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRuleRepository
        extends JpaRepository<PromotionRule, Long> {
}