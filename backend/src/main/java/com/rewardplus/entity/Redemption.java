package com.rewardplus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "redemptions")
@Getter
@Setter
@NoArgsConstructor
public class Redemption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "reward_id")
    private Reward reward;

    @Column(nullable = false)
    private Integer pointsRedeemed;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RedemptionStatus redemptionStatus;

}