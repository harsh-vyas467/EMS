package com.practice.springboot.ems.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empId;

    @Column(name= "first_name")
    private String fname;

    @Column(name= "last_name")
    private String lname;

    @Column(name= "email_id", nullable = false, unique = true)
    private String emailAddress;

    @Column(name= "place")
    private String location;

    @Enumerated(EnumType.STRING)
    private EmployeeType employeeType;

    @Column(name = "internal_notes")
    private String internalNotes; // Field we will ignore

}


