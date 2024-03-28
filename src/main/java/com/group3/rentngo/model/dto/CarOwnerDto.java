package com.group3.rentngo.model.dto;

import com.group3.rentngo.model.entity.CarOwner;
import com.group3.rentngo.model.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * DTO for {@link CarOwner}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarOwnerDto implements Serializable {

    private Long id;

    @NotBlank(message = "Mandatory information.")
    String name;

    @NotNull(message = "Mandatory information.")
    Date dateOfBirth;

    @NotBlank(message = "Mandatory information.")
    String nationalId;

    @NotBlank(message = "Mandatory information.")
    String phone;

    @NotBlank(message = "Mandatory information.")
    String email;

    @NotBlank(message = "Mandatory information.")
    String address;

    @NotBlank(message = "Mandatory information.")
    String drivingLicense;

    @NotNull(message = "Mandatory information.")
    BigDecimal wallet;

    @NotNull(message = "Mandatory information.")
    User user;
}