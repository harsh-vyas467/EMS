package com.practice.springboot.ems.mapper;

import com.practice.springboot.ems.dto.EmployeeDto;
import com.practice.springboot.ems.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto toDto(Employee employee);

    Employee toEntity(EmployeeDto employeeDto);
}
