package com.softworkshub.internassignment.repository;

import com.softworkshub.internassignment.dto.TaskResponse;
import com.softworkshub.internassignment.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

    List<Task> findAllByUserId(String userId);
//    List<Task> findAllByUser(String email);
}
