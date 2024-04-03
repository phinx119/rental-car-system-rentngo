package com.group3.rentngo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "car_image")
public class CarImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    @Column(nullable = false)
    private Blob frontImage;

    @Lob
    @Column(nullable = false)
    private Blob backImage;

    @Lob
    @Column(nullable = false)
    private Blob leftImage;

    @Lob
    @Column(nullable = false)
    private Blob rightImage;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
}
