package com.group3.rentngo.model.dto;

import com.group3.rentngo.model.entity.CarOwner;
import com.group3.rentngo.model.entity.Role;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Mandatory information.")
    String name;
}