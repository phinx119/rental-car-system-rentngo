package com.group3.rentngo.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResetPasswordDto implements Serializable {
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
