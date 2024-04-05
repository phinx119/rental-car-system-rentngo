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

    @NotBlank(message = "This field is required.")
    @Email(message = "Please enter a valid email address")
    String email;

//    @Size(min = 7, message = "Minimum length is 7.")
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
//            message = "Password must contain at least one number, one numeral, and seven characters.")
//    @NotBlank(message = "This field is required.")
    String password;

//    @NotNull(message = "This field is required.")
    boolean status;

//    @NotNull(message = "This field is required.")
    Collection<Role> roles;
}