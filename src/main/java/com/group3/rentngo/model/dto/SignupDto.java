package com.group3.rentngo.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignupDto {

    @NotBlank(message = "Mandatory information.")
    private String username;

    @NotBlank(message = "Mandatory information.")
    private String email;

    @NotBlank(message = "Mandatory information.")
    private String phone;

//    @Size(min = 8, message = "Minimum length is 8.")
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\\\d)(?=.*[@$!%*#?&])[A-Za-z\\\\d@$!%*#?&]{8,}$",
//            message = "Minimum eight characters, at least one letter, one number and one special character.")
    @NotBlank(message = "Mandatory information.")
    private String password;

    @NotBlank(message = "Mandatory information.")
    private String confirmPassword;

    @NotBlank(message = "Mandatory information.")
    private String role;

    @NotBlank(message = "Mandatory information.")
    private String agreePrivacy;
}
