package com.group3.rentngo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "car")
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    @Nationalized
    private String name;

    @Column(nullable = false, unique = true)
    @Nationalized
    private String licensePlate;

    @Column(nullable = false)
    @Nationalized
    private String brand;

    @Column(nullable = false)
    @Nationalized
    private String model;

    @Column(nullable = false)
    @Nationalized
    private String color;

    @Column(nullable = false)
    private int numberOfSeats;

    @Column(nullable = false)
    private int productionYear;

    @Column(nullable = false)
    @Nationalized
    private String transmissionType;

    @Column(nullable = false)
    @Nationalized
    private String fuelType;

    @Column(nullable = false)
    @Nationalized
    private double mileage;

    @Column(nullable = false)
    @Nationalized
    private String fuelConsumption;

    @Column(nullable = false)
    private double basePrice;

    @Column(nullable = false)
    private double deposit;

    @Column(nullable = false)
    @Nationalized
    private String address;

    @Column(nullable = false)
    @Nationalized
    private String description;

    @Column(nullable = false)
    private String additionalFunctions;

    @Column(nullable = false)
    @Nationalized
    private String termOfUse;

    @Transient
    private MultipartFile registrationPaper;
    @Column(nullable = false)
    private String registrationPaperPath;

    @Transient
    private MultipartFile certificateOfInspection;
    @Column(nullable = false)
    private String certificateOfInspectionPath;

    @Transient
    private MultipartFile insurance;
    @Column(nullable = false)
    private String insurancePath;

    @ManyToOne
    @JoinColumn(name = "car_owner_id")
    private CarOwner carOwner;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private Collection<Booking> bookings;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_image_id")
    private CarImage carImage;




}