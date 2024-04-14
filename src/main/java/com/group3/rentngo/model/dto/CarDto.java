package com.group3.rentngo.model.dto;

import com.group3.rentngo.model.entity.*;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link Car}
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarDto implements Serializable {

    private Long id;
    @NotNull(message = "Mandatory information.")
    @NotBlank(message = "Mandatory information.")
    String name;

    @NotNull(message = "Mandatory information.")
    @NotBlank(message = "Mandatory information.")
    String licensePlate;

    @NotNull(message = "Mandatory information.")
    @NotBlank(message = "Mandatory information.")
    String brand;

    @NotNull(message = "Mandatory information.")
    @NotBlank(message = "Mandatory information.")
    String model;

    @NotNull(message = "Mandatory information.")
    @NotBlank(message = "Mandatory information.")
    String color;

    @NotNull(message = "Mandatory information.")
    @Positive(message = "Must be positive number.")
    int numberOfSeats;

    @NotNull(message = "Mandatory information.")
    @Positive(message = "Must be positive number.")
    int productionYear;

    @NotNull(message = "Mandatory information.")
    @NotBlank(message = "Mandatory information.")
    String transmissionType;

    @NotNull(message = "Mandatory information.")
    @NotBlank(message = "Mandatory information.")
    String fuelType;

    @NotNull(message = "Mandatory information.")
    @Positive(message = "Must be positive number.")
    long mileage;

    @NotNull(message = "Mandatory information.")
    @NotBlank(message = "Mandatory information.")
    String fuelConsumption;

    @NotNull(message = "Mandatory information.")
    @Positive(message = "Must be positive number.")
    double basePrice;

    @NotNull(message = "Mandatory information.")
    @Positive(message = "Must be positive number.")
    double deposit;

    @NotNull(message = "Mandatory information.")
    @NotBlank(message = "Mandatory information.")
    String address;

    @NotNull(message = "Mandatory information.")
    @NotBlank(message = "Mandatory information.")
    String description;

    @NotNull(message = "Mandatory information.")
    @NotBlank(message = "Mandatory information.")
    String additionalFunctions;

    @NotNull(message = "Mandatory information.")
    @NotBlank(message = "Mandatory information.")
    String termOfUse;

    @NotNull(message = "Mandatory information.")
    MultipartFile registrationPaper;
    String registrationPaperPath;

    @NotNull(message = "Mandatory information.")
    MultipartFile certificateOfInspection;
    String certificateOfInspectionPath;

    @NotNull(message = "Mandatory information.")
    MultipartFile insurance;
    String insurancePath;

    @NotNull(message = "Mandatory information.")
    MultipartFile frontImage;
    public String frontImagePath;

    @NotNull(message = "Mandatory information.")
    MultipartFile backImage;
    public String backImagePath;

    @NotNull(message = "Mandatory information.")
    MultipartFile leftImage;
    public String leftImagePath;

    @NotNull(message = "Mandatory information.")
    MultipartFile rightImage;
    public String rightImagePath;

    @NotNull(message = "Mandatory information.")
    CarOwner carOwner;



}