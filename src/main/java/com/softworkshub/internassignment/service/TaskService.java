package com.softworkshub.internassignment.service;


import com.softworkshub.internassignment.dto.TaskRequest;
import com.softworkshub.internassignment.dto.TaskResponse;
import com.softworkshub.internassignment.entity.Task;
import com.softworkshub.internassignment.entity.Users;
import com.softworkshub.internassignment.exception.NoTaskFound;
import com.softworkshub.internassignment.repository.TaskRepository;
import com.softworkshub.internassignment.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@Slf4j
public class TaskService {


    private final UserRepository userRepository;
    private final TaskRepository taskRepository;


    public TaskService(UserRepository userRepository , TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    // Create
    public TaskResponse addTask(TaskRequest taskRequest) {
        String username = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        log.info("Adding task for user: {}", username);
        Users user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Task task = Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .status(taskRequest.getStatus() != null ? taskRequest.getStatus() : "TODO")
                .user(user).build();

        Task save = taskRepository.save(task);

        return TaskResponse.builder()
                .id(save.getId())
                .title(save.getTitle())
                .description(save.getDescription())
                .status(save.getStatus())
                .userEmail(user.getEmail())
                .createdAt(save.getCreatedAt())
                .build();
    }

    // Get All
    public List<TaskResponse> getAllTasks() {
        Users user = getCurrentUser();

        List<TaskResponse> tasks = taskRepository.findAllByUserEmail(user.getEmail());
        if (tasks.isEmpty()) {
            throw new NoTaskFound("Nothing to show");
        }
        return tasks.stream().map(task -> TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .userEmail(task.getUserEmail())
                .createdAt(task.getCreatedAt())
                .build())
                .toList();
    }

    // Get By Task Id
    public TaskResponse getTaskById(Long taskId) {
        Users user = getCurrentUser();

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NoTaskFound("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You cannot access this task");
        }

        return TaskResponse.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .userEmail(task.getUser().getEmail())
                .createdAt(task.getCreatedAt())
                .build();
    }

    // Update Task by id
    public TaskResponse updateTask(Long taskId, TaskRequest request) {

        Users user = getCurrentUser();

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NoTaskFound("Task not found"));

        // check ownership
        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You cannot update this task");
        }

        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }

        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }

        Task updated = taskRepository.save(task);

        return TaskResponse.builder()
                .id(updated.getId())
                .title(updated.getTitle())
                .description(updated.getDescription())
                .status(updated.getStatus())
                .userEmail(updated.getUser().getEmail())
                .createdAt(updated.getCreatedAt())
                .build();
    }

    // delete task by id (Only user can delete)
    public void deleteTaskById(Long taskId) {

        Users user = getCurrentUser();

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NoTaskFound("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You cannot delete this task");
        }

        taskRepository.delete(task);
    }

    // Delete All Task By User
    public void deleteAllTasksByUser() {
        Users user = getCurrentUser();

        List<Task> tasks = taskRepository.findAllByUser(user.getEmail());

        taskRepository.deleteAll(tasks);
    }


    // Get All Task By Admin
    public List<TaskResponse> getAllTasksForAdmin() {

        Users user = getCurrentUser();

        if (!user.getRole().name().equals("ROLE_ADMIN")) {
            throw new RuntimeException("Access denied");
        }

        return taskRepository.findAll().stream()
                .map(task -> TaskResponse.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .status(task.getStatus())
                        .userEmail(task.getUser().getEmail())
                        .createdAt(task.getCreatedAt())
                        .build())
                .toList();
    }


    // Delete Task By Admin
    public void deleteTaskByIdAdmin(Long taskId) {

        Users user = getCurrentUser();

        if (!user.getRole().name().equals("ROLE_ADMIN")) {
            throw new RuntimeException("Access denied");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NoTaskFound("Task not found"));

        taskRepository.delete(task);
    }

    private Users getCurrentUser() {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
