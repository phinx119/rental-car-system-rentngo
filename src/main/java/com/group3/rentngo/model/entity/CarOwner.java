package com.group3.rentngo.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "car_owner")
public class CarOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date dateOfBirth;

    @Column(nullable = false, unique = true)
    private String nationalId;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, unique = true)
    private String drivingLicense;

    @Column(nullable = false)
    private BigDecimal wallet;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "carOwner", cascade = CascadeType.ALL)
    private Collection<Car> cars;
}