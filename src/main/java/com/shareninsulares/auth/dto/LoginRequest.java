package com.shareninsulares.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Student ID is required")
    private String studentId;

    @NotBlank(message = "Password is required")
    private String password;
}
