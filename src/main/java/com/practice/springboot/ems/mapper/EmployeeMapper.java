package com.practice.springboot.ems.mapper;

import com.practice.springboot.ems.dto.EmployeeDto;
import com.practice.springboot.ems.entity.Employee;
import com.practice.springboot.ems.entity.EmployeeType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    // Entity to DTO
    @Mappings({
            @Mapping(source = "empId", target = "id"),
            @Mapping(source = "fname", target = "firstName"),
            @Mapping(source = "lname", target = "lastName"),
            @Mapping(source = "emailAddress", target = "email"),
            @Mapping(source = "location", target = "place"),
            @Mapping(source = "employeeType", target = "type"),
//            @Mapping(target = "internalNotes", ignore = true) // not needed in DTO
    })
    EmployeeDto toDto(Employee employee);

    // DTO to Entity
    @Mappings({
            @Mapping(source = "id", target = "empId"),
            @Mapping(source = "firstName", target = "fname"),
            @Mapping(source = "lastName", target = "lname"),
            @Mapping(source = "email", target = "emailAddress"),
            @Mapping(source = "place", target = "location"),
            @Mapping(target = "employeeType", expression = "java(mapType(dto.getType()))"),
            @Mapping(target = "internalNotes", ignore = true)
    })
    Employee toEntity(EmployeeDto dto);


    @Mappings({
            @Mapping(source = "firstName", target = "fname"),
            @Mapping(source = "lastName", target = "lname"),
            @Mapping(source = "email", target = "emailAddress"),
            @Mapping(source = "place", target = "location"),
            @Mapping(target = "employeeType", expression = "java(mapType(dto.getType()))"),
            @Mapping(target = "internalNotes", ignore = true)
    })//only mention those mapping that you want to update,remaining fields are ignored
    void updateEmployeeFromDto(EmployeeDto dto, @MappingTarget Employee employee);

    //@MappingTarget - Take values from dto, and copy them into the existing employee object instead of creating a new one.
    //In short - "Don’t create a new object — just update this existing object."

    // Helper to map String to Enum
    default EmployeeType mapType(String type) {
        try {
            return type != null ? EmployeeType.valueOf(type) : null;
        } catch (IllegalArgumentException e) {
            return null; // or throw custom exception
        }
    }
}


