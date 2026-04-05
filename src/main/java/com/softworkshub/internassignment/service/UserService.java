package com.softworkshub.internassignment.service;


import com.softworkshub.internassignment.dto.AuthResponse;
import com.softworkshub.internassignment.dto.LoginRequest;
import com.softworkshub.internassignment.dto.RegisterRequest;
import com.softworkshub.internassignment.entity.Users;
import com.softworkshub.internassignment.exception.InvalidCredentialsException;
import com.softworkshub.internassignment.repository.UserRepository;
import com.softworkshub.internassignment.util.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse registerUser(RegisterRequest request) {

        if (checkExists(request.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        Users user = Users.builder()

                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        log.info("Registering user: {}", user);
        userRepository.save(user);
        log.info("Registering user After save function : {}", user);

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }


    public AuthResponse registerAdmin(RegisterRequest request) {

        String email = request.getEmail();
        if (checkExists(email)){
            throw new RuntimeException("Email already exists");
        }

        Users user = Users.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_ADMIN)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    public AuthResponse loginUser(LoginRequest request) {

        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    private boolean checkExists(String email) {
        Optional<Users> user = userRepository.findByEmail(email);
        return user.isPresent();
    }
}
