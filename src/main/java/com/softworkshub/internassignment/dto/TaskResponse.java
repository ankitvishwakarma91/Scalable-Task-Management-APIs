package com.softworkshub.internassignment.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private String status;
    private String userEmail;
    private LocalDateTime createdAt;
}
