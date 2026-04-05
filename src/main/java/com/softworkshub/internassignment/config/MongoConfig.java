package com.softworkshub.internassignment.config;


import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.bson.internal.BsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "com.softworkshub.internassignment.repository")
@Slf4j
public class MongoConfig {



}
