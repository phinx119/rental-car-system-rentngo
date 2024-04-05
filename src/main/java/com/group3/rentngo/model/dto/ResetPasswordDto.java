package com.group3.rentngo.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResetPasswordDto {
    @NotBlank(message = "This field is required.")
    @Email
    private String email;

    @Size(min = 7, message = "Minimum length is 7.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "Password must contain at least one number, one numeral, and seven characters.")
    @NotBlank(message = "This field is required.")
    private String password;

    @NotBlank(message = "This field is required.")
    private String confirmPassword;
}
