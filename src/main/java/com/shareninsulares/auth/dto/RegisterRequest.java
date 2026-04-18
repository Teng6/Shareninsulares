package com.shareninsulares.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Student ID is required")
    private String studentId;

    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email")
    @Pattern(
            regexp = "^[\\w.+-]+@bpsu\\.edu\\.ph$",
            message = "Only @bpsu.edu.ph emails are allowed"
    )
    private String email;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Campus is required")
    private String campus;
}
