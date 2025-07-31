package com.practice.springboot.ems.mongodb.document;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "ems_logs") //  MongoDB collection name
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeLog {

    @Id
    private String id;

    private Long employeeId;
    private String action;        // e.g., "CREATED", "UPDATED", etc.
    private String performedBy;
    private LocalDateTime timestamp = LocalDateTime.now();



}

