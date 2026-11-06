package com.rewardplus.repository;

import com.rewardplus.entity.CustomerSegmentMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerSegmentMappingRepository
        extends JpaRepository<CustomerSegmentMapping, Long> {
}