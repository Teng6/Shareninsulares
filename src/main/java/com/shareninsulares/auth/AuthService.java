package com.shareninsulares.auth;


import com.shareninsulares.auth.dto.AuthResponse;
import com.shareninsulares.auth.dto.LoginRequest;
import com.shareninsulares.auth.dto.RegisterRequest;
import com.shareninsulares.security.JwtUtil;
import com.shareninsulares.user.User;
import com.shareninsulares.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("Email already registered");
        }
        if(userRepository.existsByStudentId(request.getStudentId())){
            throw new IllegalArgumentException("Student ID already registered");
        }

        User user = User.builder()
                .studentId(request.getStudentId())
                .email(request.getEmail())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .campus(request.getCampus())
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(
                token,
                user.getId(),
                user.getStudentId(),
                user.getEmail(),
                user.getFullName(),
                user.getCampus(),
                user.getRole()
        );
    }

    public AuthResponse login(LoginRequest request){
        User user = userRepository.findByStudentId(request.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID"));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(
                token,
                user.getId(),
                user.getStudentId(),
                user.getEmail(),
                user.getFullName(),
                user.getCampus(),
                user.getRole()
        );
    }
}
