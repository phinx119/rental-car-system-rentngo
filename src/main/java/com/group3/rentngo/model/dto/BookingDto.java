package com.group3.rentngo.model.dto;

import com.group3.rentngo.model.entity.Booking;
import com.group3.rentngo.model.entity.Car;
import com.group3.rentngo.model.entity.CarOwner;
import com.group3.rentngo.model.entity.Customer;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * DTO for {@link Booking}
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingDto implements Serializable {

    private Long id;

    @FutureOrPresent(message = "Not earlier than current date.")
    Date startDateTime;

    @Future(message = "Not earlier than current date.")
    Date endDateTime;

    @NotNull(message = "Mandatory information.")
    Customer customer;

    @NotBlank(message = "Mandatory information.")
    String paymentMethod;

    boolean status;

    @NotNull(message = "Mandatory information.")
    Collection<Car> cars;
}