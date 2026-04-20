package com.shareninsulares.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private Long id;
    private String studentId;
    private String email;
    private String fullName;
    private String campus;
    private String role;

}
