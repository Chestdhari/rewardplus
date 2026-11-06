package com.rewardplus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String customerCode;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String phone;

    private LocalDate dateOfBirth;

    private String address;

    private String city;

    private String state;

    private String country;

    private String pinCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MembershipLevel membershipLevel = MembershipLevel.BRONZE;

    @Column(nullable = false)
    private Integer currentPoints = 0;

    @Column(nullable = false)
    private Boolean active = true;

    /**
     * Each Customer has one User account used for authentication.
     * Existing customers created before this feature may have a null user,
     * so we don't enforce nullable = false yet.
     */
//     Without it, every time you fetch a customer, Hibernate also fetches the associated user immediately, even if you don't need it.

// With LAZY, the user is only loaded when you actually access customer.getUser().
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

}