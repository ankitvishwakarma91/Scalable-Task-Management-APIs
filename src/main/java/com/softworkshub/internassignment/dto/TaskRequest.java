package com.softworkshub.internassignment.dto;

import com.softworkshub.internassignment.util.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskRequest {

    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private TaskStatus status;
}
