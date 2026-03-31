package com.softworkshub.internassignment.controller;


import com.softworkshub.internassignment.dto.TaskResponse;
import com.softworkshub.internassignment.entity.Users;
import com.softworkshub.internassignment.service.TaskService;
import com.softworkshub.internassignment.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin APIs", description = "Administrative operations")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final UserService userService;
    private final TaskService taskService;

    public AdminController(UserService userService , TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @Operation(
            summary = "Get all users",
            description = "Fetch all users (Admin only)"
    )
    @ApiResponse(responseCode = "200", description = "Users fetched successfully")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @GetMapping("/users")
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @Operation(
            summary = "Get all tasks",
            description = "Fetch all tasks (Admin only)"
    )
    @ApiResponse(responseCode = "200", description = "Tasks fetched successfully")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.ok().body(taskService.getAllTasksForAdmin());
    }

    @Operation(
            summary = "Delete task by ID",
            description = "Delete any task (Admin only)"
    )
    @ApiResponse(responseCode = "200", description = "Task deleted successfully")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTaskByIdAdmin(@PathVariable(name = "id") Long taskId) {
        taskService.deleteTaskByIdAdmin(taskId);
        return ResponseEntity.ok().build();
    }


}
