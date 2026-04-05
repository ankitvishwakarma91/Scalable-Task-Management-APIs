package com.softworkshub.internassignment;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class InternAssignmentApplication {

    @Autowired
    private MongoTemplate mongoTemplate;

    public static void main(String[] args) {
        SpringApplication.run(InternAssignmentApplication.class, args);



    }
    @PostConstruct
    public void display(){
        System.out.println("get db name : " + mongoTemplate.getDb().getName());
        System.out.println("Users count: " + mongoTemplate.getCollection("users").countDocuments());
    }

}
