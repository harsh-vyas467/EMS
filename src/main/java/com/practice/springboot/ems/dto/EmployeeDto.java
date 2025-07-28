package com.practice.springboot.ems.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeDto {// Data Transfer Object (DTO)
    // Fields populated from incoming JSON request
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String place;
    private String type; // maps to EmployeeType enum
}
