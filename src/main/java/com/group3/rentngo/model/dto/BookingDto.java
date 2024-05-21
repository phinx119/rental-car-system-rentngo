package com.group3.rentngo.model.dto;

import com.group3.rentngo.model.entity.Booking;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link Booking}
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingDto implements Serializable {

    private Long id;

    UpdateBookingDetailDto updateBookingDetailDto;

    @NotNull(message = "Mandatory information.")
    CustomerDto customerDto;

    @NotNull(message = "Mandatory information.")
    String paymentMethod;

    String status;

    Long carId;
}