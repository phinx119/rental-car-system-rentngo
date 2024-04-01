package com.group3.rentngo.model.dto;

import com.group3.rentngo.model.entity.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

/**
 * DTO for {@link Feedback}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FeedbackDto implements Serializable {

    private Long id;

    @Min(0)
    @Max(5)
    @PositiveOrZero(message = "Must be positive number.")
    int ratings;

    @NotBlank(message = "Mandatory information.")
    String content;

    @NotNull
    Date dateTime;

    @NotNull(message = "Mandatory information.")
    Booking booking;
}