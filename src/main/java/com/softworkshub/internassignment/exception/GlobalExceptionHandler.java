package com.softworkshub.internassignment.exception;

import com.softworkshub.internassignment.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponse buildError(String message, int status, HttpServletRequest request) {
        return ErrorResponse.builder()
                .message(message)
                .status(status)
                .timestamp(System.currentTimeMillis())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        return ResponseEntity
                .status(400)
                .body(buildError(message, 400, request));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(
            RuntimeException ex,
            HttpServletRequest request) {

        return ResponseEntity
                .status(400)
                .body(buildError(ex.getMessage(), 400, request));
    }

    @ExceptionHandler(JwtTokenNotProvidedException.class)
    public ResponseEntity<ErrorResponse> jwtTokenNotProvided(
            JwtTokenNotProvidedException ex,
            HttpServletRequest request) {

        return ResponseEntity
                .status(401)
                .body(buildError(ex.getMessage(), 401, request));
    }

    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity<ErrorResponse> jwtTokenExpired(
            JwtTokenExpiredException ex,
            HttpServletRequest request) {

        return ResponseEntity
                .status(401)
                .body(buildError(ex.getMessage(), 401, request));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> invalidEmailOrPassword(
            InvalidCredentialsException ex,
            HttpServletRequest request) {

        return ResponseEntity
                .status(401)
                .body(buildError(ex.getMessage(), 401, request));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> noTaskFound(NoTaskFound ex, HttpServletRequest request){
        return ResponseEntity.status(404)
                .body(buildError(ex.getMessage(), 404, request));
    }
}
