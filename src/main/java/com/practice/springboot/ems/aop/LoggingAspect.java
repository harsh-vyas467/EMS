package com.practice.springboot.ems.aop;

import com.practice.springboot.ems.dto.EmployeeDto;
import com.practice.springboot.ems.entity.Employee;
import com.practice.springboot.ems.mapper.EmployeeMapper;
import com.practice.springboot.ems.mongodb.service.EmployeeLogService;
import com.practice.springboot.ems.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final EmployeeLogService employeeLogService;

    private final EmployeeMapper employeeMapper;

    private final EmployeeRepository employeeRepository;

    // Log before method execution
    @Before("execution(* com.practice.springboot.ems..*(..))")
    public void logMethodCall(JoinPoint joinPoint) {
        log.info("Method called: {} with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    // Log after method successfully returns
    @AfterReturning(pointcut = "execution(* com.practice.springboot.ems..*(..))", returning = "result")
    public void logMethodReturn(JoinPoint joinPoint, Object result) {
        log.info("Method returned: {} with result: {}", joinPoint.getSignature(), result);
    }

    // Log when exception is thrown
    @AfterThrowing(pointcut = "execution(* com.practice.springboot.ems..*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        log.error("Exception in method: {} with message: {}", joinPoint.getSignature(), ex.getMessage());
    }

    // mongo

    @AfterReturning(
            pointcut = "execution(* com.practice.springboot.ems.service.impl.EmployeeServiceImpl.createEmployee(..))",
            returning = "result"
    )
    public void logEmployeeCreate(JoinPoint joinPoint, Object result) {
        if (result instanceof EmployeeDto dto) {
            employeeLogService.logAction(dto.getId(),"CREATE", "SYSTEM", dto);
        }
    }

    @Around("execution(* com.practice.springboot.ems.service.impl.EmployeeServiceImpl.updateEmployee(..))")
    public Object logEmployeeUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof Long employeeId) {

            // Fetch old state before update
            Employee managedOldEmp = employeeRepository.findById(employeeId).orElse(null);
            Employee oldEmployee = (managedOldEmp != null) ? cloneEmployee(managedOldEmp) : null;


            // Proceed with actual update
            Object result = joinPoint.proceed();

            if (result instanceof EmployeeDto updatedDto) {
                Employee updatedEmployee = employeeMapper.toEntity(updatedDto);

                // Log difference
                employeeLogService.logUpdateAction(oldEmployee, updatedEmployee, "SYSTEM");
            }

            return result;
        }

        return joinPoint.proceed();
    }

    private Employee cloneEmployee(Employee source) {
        Employee copy = new Employee();
        copy.setEmpId(source.getEmpId());
        copy.setFname(source.getFname());
        copy.setLname(source.getLname());
        copy.setLocation(source.getLocation());
        copy.setEmailAddress(source.getEmailAddress());
        copy.setEmployeeType(source.getEmployeeType());
        // Add more fields as needed
        return copy;
    }

    @Around("execution(* com.practice.springboot.ems.service.impl.EmployeeServiceImpl.deleteEmployee(..))")
    public Object logEmployeeDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof Long employeeId) {
            // Step 1: Fetch employee data before delete
            // Inject the actual service/dao to fetch it (not shown here)
            EmployeeDto deletedEmployeeDto = fetchEmployeeDtoById(employeeId); // You'll implement this

            // Step 2: Proceed with deletion
            Object result = joinPoint.proceed();

            // Step 3: Log deleted data
            employeeLogService.logAction(employeeId, "DELETE", "SYSTEM", deletedEmployeeDto);

            return result;
        }
        return joinPoint.proceed();
    }

    private EmployeeDto fetchEmployeeDtoById(Long id) {
        Employee emp = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id));
        return employeeMapper.toDto(emp);
    }



}
