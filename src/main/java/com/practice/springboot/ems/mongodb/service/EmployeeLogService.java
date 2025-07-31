package com.practice.springboot.ems.mongodb.service;


import com.practice.springboot.ems.mongodb.document.EmployeeLog;
import com.practice.springboot.ems.mongodb.repository.EmployeeLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeLogService {

    @Autowired
    private EmployeeLogRepository logRepository;

    public void logAction(Long employeeId, String action, String performedBy) {
        EmployeeLog log = new EmployeeLog();
        log.setEmployeeId(employeeId);
        log.setAction(action);
        log.setPerformedBy(performedBy);
        logRepository.save(log);
    }
}

