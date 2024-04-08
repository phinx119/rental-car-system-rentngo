package com.group3.rentngo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.util.Collection;

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

    @Transient
    @Column(nullable = false)
    private MultipartFile registrationPaper;

    @Transient
    @Column(nullable = false)
    private MultipartFile certificateOfInspection;

    @Transient
    @Column(nullable = false)
    private MultipartFile insurance;

    @ManyToOne
    @JoinColumn(name = "car_owner_id")
    private CarOwner carOwner;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private Collection<Booking> bookings;
}