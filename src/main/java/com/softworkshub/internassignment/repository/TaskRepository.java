package com.softworkshub.internassignment.repository;

import com.softworkshub.internassignment.dto.TaskResponse;
import com.softworkshub.internassignment.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<TaskResponse> findAllByUserEmail(String email);
    List<Task> findAllByUser(String email );
}
