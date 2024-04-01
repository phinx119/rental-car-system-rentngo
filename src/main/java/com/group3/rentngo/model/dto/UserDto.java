package com.group3.rentngo.model.dto;

import com.group3.rentngo.model.entity.*;
import jakarta.validation.constraints.*;
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

//    @Size(min = 8, message = "Minimum length is 8.")
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\\\d)(?=.*[@$!%*#?&])[A-Za-z\\\\d@$!%*#?&]{8,}$",
//            message = "Minimum eight characters, at least one letter, one number and one special character.")
    @NotBlank(message = "Mandatory information.")
    String password;

    @NotNull(message = "Mandatory information.")
    boolean status;

    @NotNull(message = "Mandatory information.")
    Collection<Role> roles;
}