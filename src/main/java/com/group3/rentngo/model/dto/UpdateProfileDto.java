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
public class UpdateProfileDto implements Serializable {

    private Long id;

    String role;

    @NotBlank(message = "This field is required.")
    String name;

    //@NotBlank(message = "This field is required.")
    //@FutureOrPresent(message = "Not earlier than current date.")
    String dateOfBirth;

    @NotBlank(message = "This field is required.")
    String nationalId;

    @NotBlank(message = "This field is required.")
    String phone;

    @NotBlank(message = "This field is required.")
    String email;

    @NotBlank(message = "This field is required.")
    String city;

    @NotBlank(message = "This field is required.")
    String district;

    @NotBlank(message = "This field is required.")
    String ward;

    @NotBlank(message = "This field is required.")
    String houseNumberAndStreet;

    @NotBlank(message = "This field is required.")
    String drivingLicense;
}
