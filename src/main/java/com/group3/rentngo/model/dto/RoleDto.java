package com.group3.rentngo.model.dto;

import com.group3.rentngo.model.entity.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link Role}
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleDto implements Serializable {

    private Long id;

    @NotBlank(message = "This field is required.")
    String name;
}