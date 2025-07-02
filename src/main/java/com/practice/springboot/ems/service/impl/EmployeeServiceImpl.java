package com.practice.springboot.ems.service.impl;

import com.practice.springboot.ems.dto.EmployeeDto;
import com.practice.springboot.ems.entity.Employee;
import com.practice.springboot.ems.mapper.EmployeeMapper;
import com.practice.springboot.ems.repository.EmployeeRepository;
import com.practice.springboot.ems.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;


    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }
}
