package com.softworkshub.internassignment.controller;


import com.softworkshub.internassignment.dto.AuthResponse;
import com.softworkshub.internassignment.dto.LoginRequest;
import com.softworkshub.internassignment.dto.RegisterRequest;
import com.softworkshub.internassignment.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth APIs", description = "Authentication operations")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Tag(name = "Auth APIs", description = "Authentication operations")
    @GetMapping("/health")
    public String health(){
        return "OK";
    }

    @Operation(
            summary = "Register user",
            description = "Register a new user or admin"
    )
    @ApiResponse(responseCode = "201", description = "User registered successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.registerUser(request));
    }

    @Operation(
            summary = "Login user",
            description = "Authenticate user and return JWT token"
    )
    @ApiResponse(responseCode = "200", description = "Login successful")
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(userService.loginUser(request));
    }

}
