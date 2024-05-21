package com.group3.rentngo.model.dto;

import com.group3.rentngo.model.entity.Customer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link Customer}
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDto implements Serializable {

    private Long id;

    @NotBlank(message = "This field is required.")
    String name;

    //@NotNull(message = "This field is required.")
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

    @NotNull(message = "This field is required.")
    BigDecimal wallet;
}