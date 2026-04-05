package com.softworkshub.internassignment.dto;

import com.softworkshub.internassignment.util.TaskStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
public class TaskResponse {

    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private String userEmail;
    private LocalDateTime createdAt;
}
