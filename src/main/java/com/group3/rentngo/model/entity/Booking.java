package com.group3.rentngo.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_no", nullable = false)
    private Long id;

    @Column(nullable = false)
    private Date startDateTime;

    @Column(nullable = false)
    private Date endDateTime;

    @ManyToOne
    @JoinColumn(name = "driver_information")
    private Customer customer;

    @Column(nullable = false)
    private String paymentMethod;

    @Column(nullable = false)
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
}