package com.practice.springboot.ems.mongodb.service;


import com.practice.springboot.ems.dto.EmployeeDto;
import com.practice.springboot.ems.entity.Employee;
import com.practice.springboot.ems.mapper.EmployeeMapper;
import com.practice.springboot.ems.mongodb.document.EmployeeLog;
import com.practice.springboot.ems.mongodb.repository.EmployeeLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmployeeLogService {

    private final EmployeeLogRepository employeeLogRepository;

    private final EmployeeMapper employeeMapper;

    public void logAction(Long employeeId, String action, String performedBy, EmployeeDto dto) {
        EmployeeLog log = new EmployeeLog();
        log.setEmployeeId(employeeId);
        log.setAction(action);
        log.setPerformedBy(performedBy);
        if("CREATE".equalsIgnoreCase((action))) {
            log.setNewData(employeeMapper.toEntity(dto));
        }

        // If it's a DELETE, capture the deleted data
        if ("DELETE".equalsIgnoreCase(action) && dto != null) {
            Map<String, Object> deletedData = new HashMap<>();
            deletedData.put("firstName", dto.getFirstName());
            deletedData.put("lastName", dto.getLastName());
            deletedData.put("email", dto.getEmail());
            deletedData.put("place", dto.getPlace());
            deletedData.put("type", dto.getType());
            log.setDeletedData(deletedData);
        }

        employeeLogRepository.save(log);
    }

    public void logUpdateAction(Employee oldEmp, Employee updatedEmp, String performedBy) {
        Map<String, Map<String, Object>> changes = new HashMap<>();

        if (!Objects.equals(oldEmp.getFname(), updatedEmp.getFname())) {
            changes.put("fname", Map.of("old", oldEmp.getFname(), "new", updatedEmp.getFname()));
        }
        if (!Objects.equals(oldEmp.getLname(), updatedEmp.getLname())) {
            changes.put("lname", Map.of("old", oldEmp.getLname(), "new", updatedEmp.getLname()));
        }
        if (!Objects.equals(oldEmp.getLocation(), updatedEmp.getLocation())) {
            changes.put("location", Map.of("old", oldEmp.getLocation(), "new", updatedEmp.getLocation()));
        }
        if (!Objects.equals(oldEmp.getEmployeeType(), updatedEmp.getEmployeeType())) {
            changes.put("employeeType", Map.of("old", oldEmp.getEmployeeType(), "new", updatedEmp.getEmployeeType()));
        }
        if (!Objects.equals(oldEmp.getEmailAddress(), updatedEmp.getEmailAddress())) {
            changes.put("emailAddress", Map.of("old", oldEmp.getEmailAddress(), "new", updatedEmp.getEmailAddress()));
        }

        if (!changes.isEmpty()) {
            EmployeeLog updatedData = new EmployeeLog();
            updatedData.setEmployeeId(oldEmp.getEmpId());
            updatedData.setAction("UPDATE");
            updatedData.setPerformedBy(performedBy);
            updatedData.setFieldChanges(changes);
            updatedData.setTimestamp(LocalDateTime.now());

            // Add full snapshots of before and after
            updatedData.setOldData(oldEmp);
            updatedData.setNewData(updatedEmp);

            employeeLogRepository.save(updatedData);
        }
    }


}

