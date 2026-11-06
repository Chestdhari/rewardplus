package com.rewardplus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer_segments")
@Getter
@Setter
@NoArgsConstructor
public class CustomerSegment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String segmentName;

    private String description;

    @Column(nullable = false)
    private Boolean active = true;

}