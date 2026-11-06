package com.rewardplus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "loyalty_accounts")
@Getter
@Setter
@NoArgsConstructor
public class LoyaltyAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false, unique = true)
    private Customer customer;

    @Column(nullable = false)
    private Integer currentPoints = 0;

    @Column(nullable = false)
    private Integer lifetimePoints = 0;

    @Column(nullable = false)
    private Integer availablePoints = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MembershipLevel membershipLevel = MembershipLevel.BRONZE;

    @Column(nullable = false)
    private Boolean active = true;

}