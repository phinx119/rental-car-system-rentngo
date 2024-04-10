package com.group3.rentngo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

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


    @Column(nullable = false)
    public String frontImagePath;


    @Column(nullable = false)
    public String backImagePath;


    @Column(nullable = false)
    public String leftImagePath;


    @Column(nullable = false)
    public String rightImagePath;


    @OneToOne(mappedBy = "carImage", cascade = CascadeType.ALL)
    private Car car;


}
