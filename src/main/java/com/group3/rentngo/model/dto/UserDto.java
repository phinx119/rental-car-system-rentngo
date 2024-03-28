package com.group3.rentngo.model.dto;

import com.group3.rentngo.model.entity.CarOwner;
import com.group3.rentngo.model.entity.Role;
import com.group3.rentngo.model.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.Collection;

/**
 * DTO for {@link User}
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto implements Serializable {

    private Long id;

    @NotBlank(message = "Mandatory information.")
    String username;

    @Size(min = 8, message = "Minimum length is 8.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\\\d)(?=.*[@$!%*#?&])[A-Za-z\\\\d@$!%*#?&]{8,}$",
            message = "Minimum eight characters, at least one letter, one number and one special character.")
    @NotBlank(message = "Mandatory information.")
    String password;

    @NotNull(message = "Mandatory information.")
    Collection<Role> roles;
}