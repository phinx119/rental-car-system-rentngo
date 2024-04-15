package com.group3.rentngo.model.dto;

import com.group3.rentngo.model.entity.Car;
import com.group3.rentngo.model.entity.CarImage;
import com.group3.rentngo.model.entity.CarOwner;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * DTO for {@link Car}
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarDto implements Serializable {

    private Long id;


    private String name;

    @NotBlank(message = "License plate is required.")
    private String licensePlate;

    @NotBlank(message = "Brand is required.")
    private String brand;

    @NotBlank(message = "Model is required.")
    private String model;

    @NotBlank(message = "Color is required.")
    private String color;

    @Positive(message = "Number of seats must be a positive number.")
    private int numberOfSeats;

    @Positive(message = "Production year must be a positive number.")
    private int productionYear;

    @NotBlank(message = "Transmission type is required.")
    private String transmissionType;

    @NotBlank(message = "Fuel type is required.")
    private String fuelType;

    @Positive(message = "Mileage must be a positive number.")
    private double mileage;

    @NotBlank(message = "Fuel consumption is required.")
    private String fuelConsumption;

    @Positive(message = "Base price must be a positive number.")
    private double basePrice;

    @Positive(message = "Deposit must be a positive number.")
    private double deposit;

    @NotBlank(message = "This field is required.")
    String city;

    @NotBlank(message = "This field is required.")
    String district;

    @NotBlank(message = "This field is required.")
    String ward;

    @NotBlank(message = "This field is required.")
    String houseNumberAndStreet;

    private String description;


    private String additionalFunctions;


    private String termOfUse;


    private MultipartFile registrationPaper;
    private String registrationPaperPath;


    private MultipartFile certificateOfInspection;
    private String certificateOfInspectionPath;


    private MultipartFile insurance;
    private String insurancePath;


    private MultipartFile frontImage;
    private String frontImagePath;


    private MultipartFile backImage;
    private String backImagePath;


    private MultipartFile leftImage;
    private String leftImagePath;


    private MultipartFile rightImage;
    private String rightImagePath;

    private CarOwner carOwner;

    private CarImage carImage;
}