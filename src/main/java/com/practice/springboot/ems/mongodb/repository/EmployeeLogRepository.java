package com.practice.springboot.ems.mongodb.repository;

import com.practice.springboot.ems.mongodb.document.EmployeeLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeLogRepository extends MongoRepository<EmployeeLog, String> {
    // Optional: custom query methods
}
