package com.practice.springboot.ems.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeDto {// Data Transfer Object (DTO)
    // Fields populated from incoming JSON request
    @JsonProperty("id")
    private Long id;

    @NotBlank(message = "First name is required")
    @JsonProperty("firstName")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @JsonProperty("lastName")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Place is required")
    @JsonProperty("place")
    private String place;

    @NotNull(message = "Employee type is required")
    @JsonProperty("type")
    private String type; // maps to EmployeeType enum
}
