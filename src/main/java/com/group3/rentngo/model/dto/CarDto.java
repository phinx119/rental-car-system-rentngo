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

    private String description;

    @NotBlank(message = "Additional functions are required.")
    private String additionalFunctions;

    @NotBlank(message = "Term of use is required.")
    private String termOfUse;

    @NotNull(message = "Registration paper is required.")
    private MultipartFile registrationPaper;
    private String registrationPaperPath;

    @NotNull(message = "Certificate of inspection is required.")
    private MultipartFile certificateOfInspection;
    private String certificateOfInspectionPath;

    @NotNull(message = "Insurance document is required.")
    private MultipartFile insurance;
    private String insurancePath;

    @NotNull(message = "Front image is required.")
    private MultipartFile frontImage;
    private String frontImagePath;

    @NotNull(message = "Back image is required.")
    private MultipartFile backImage;
    private String backImagePath;

    @NotNull(message = "Left image is required.")
    private MultipartFile leftImage;
    private String leftImagePath;

    @NotNull(message = "Right image is required.")
    private MultipartFile rightImage;
    private String rightImagePath;

    private CarOwner carOwner;

    private CarImage carImage;
}