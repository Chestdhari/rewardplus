package com.rewardplus.repository;

import com.rewardplus.entity.CustomerSegment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerSegmentRepository
        extends JpaRepository<CustomerSegment, Long> {
}