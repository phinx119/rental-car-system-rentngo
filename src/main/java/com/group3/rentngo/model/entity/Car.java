package com.group3.rentngo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String licensePlate;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private int numberOfSeats;

    @Column(nullable = false)
    private int productionYear;

    @Column(nullable = false)
    private String transmissionType;

    @Column(nullable = false)
    private String fuelType;

    @Column(nullable = false)
    private long mileage;

    @Column(nullable = false)
    private String fuelConsumption;

    @Column(nullable = false)
    private double basePrice;

    @Column(nullable = false)
    private double deposit;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String additionalFunctions;

    @Column(nullable = false)
    private String termOfUse;

    @Lob
    @Column
    private Blob image;

    @ManyToOne
    @JoinColumn(name = "car_owner_id")
    private CarOwner carOwner;

    @ManyToMany(mappedBy = "cars")
    private List<Booking> bookings;
}