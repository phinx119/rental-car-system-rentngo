package com.group3.rentngo.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateBookingDetailDto implements Serializable {
    @NotBlank(message = "Pick up location cannot be blank")
    private String pickUpLocation;

    @NotBlank(message = "Pick up date cannot be blank")
    private String pickUpDate;

    @NotBlank(message = "Pick up time cannot be blank")
    private String pickUpTime;

    @NotBlank(message = "Drop off date cannot be blank")
    private String dropOffDate;

    @NotBlank(message = "Drop off time cannot be blank")
    private String dropOffTime;
}
