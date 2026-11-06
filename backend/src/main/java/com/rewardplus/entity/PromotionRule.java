package com.rewardplus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "promotion_rules")
@Getter
@Setter
@NoArgsConstructor
public class PromotionRule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @Column(nullable = false)
    private BigDecimal minimumPurchaseAmount;

    @Column(nullable = false)
    private Integer minimumPointsAward;

}