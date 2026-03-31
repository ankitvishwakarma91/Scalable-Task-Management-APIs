package com.softworkshub.internassignment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private String message;
    private int status;
    private long timestamp;
    private String path;
}