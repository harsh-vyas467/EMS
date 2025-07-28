package com.practice.springboot.ems.service.impl;

import com.practice.springboot.ems.dto.EmployeeDto;
import com.practice.springboot.ems.entity.Employee;
import com.practice.springboot.ems.exception.ResourceNotFoundException;
import com.practice.springboot.ems.mapper.EmployeeMapper;
import com.practice.springboot.ems.repository.EmployeeRepository;
import com.practice.springboot.ems.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;


    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Employee employee = employeeMapper.toEntity(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee does not exist with given id : "+ employeeId));

        return employeeMapper.toDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map((employee) -> employeeMapper.toDto(employee))
                .collect(Collectors.toList())
                ;
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee does not exist with given id : " + employeeId)
        );

        // Use MapStruct to update the fields
        employeeMapper.updateEmployeeFromDto(updatedEmployee, employee); //Will update employee from values of updatedEmployee

        Employee updatedEmployeeObj = employeeRepository.save(employee);
        return employeeMapper.toDto(updatedEmployeeObj);
    }


    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee does not exist with given id : "+ employeeId)
        );

        employeeRepository.deleteById(employeeId);

    }
}
