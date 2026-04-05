package com.softworkshub.internassignment.controller;


import com.softworkshub.internassignment.dto.TaskRequest;
import com.softworkshub.internassignment.dto.TaskResponse;
import com.softworkshub.internassignment.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Task APIs", description = "Operation Related to User tasks")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService ) {
        this.taskService = taskService;
    }

    @ApiResponse(responseCode = "200", description = "Task created successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @Operation(summary = "Create Task", description = "Create a new task for logged-in user")
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {
        return ResponseEntity.accepted().body(taskService.addTask(taskRequest));
    }

    @Operation(
            summary = "Get all tasks",
            description = "Fetch all tasks for the authenticated user"
    )
    @ApiResponse(responseCode = "200", description = "Tasks fetched successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTask() {
        return ResponseEntity.ok().body(taskService.getAllTasks());
    }

    @Operation(
            summary = "Get task by ID",
            description = "Fetch a specific task by its ID"
    )
    @ApiResponse(responseCode = "200", description = "Task fetched successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@RequestParam String id) {
        return ResponseEntity.ok().body(taskService.getTaskById(id));
    }

    @Operation(
            summary = "Update task by ID",
            description = "Update an existing task"
    )
    @ApiResponse(responseCode = "202", description = "Task updated successfully")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable(name = "id") String taskId, @RequestBody TaskRequest taskRequest) {
        return ResponseEntity.accepted().body(taskService.updateTask(taskId,taskRequest));
    }

    @Operation(
            summary = "Delete task by ID",
            description = "Delete a specific task"
    )
    @ApiResponse(responseCode = "200", description = "Task deleted successfully")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable String id) {
        taskService.deleteTaskById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Delete all tasks",
            description = "Delete all tasks for the authenticated user"
    )
    @ApiResponse(responseCode = "200", description = "All tasks deleted")
    @DeleteMapping
    public ResponseEntity<?> deleteAllTaskByUser() {
        taskService.deleteAllTasksByUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
