package com.practice.springboot.ems.aop;

import com.practice.springboot.ems.dto.EmployeeDto;
import com.practice.springboot.ems.mongodb.service.EmployeeLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final EmployeeLogService employeeLogService;

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
            employeeLogService.logAction(dto.getId(),"CREATE", "SYSTEM");
        }
    }

    @AfterReturning(
            pointcut = "execution(* com.practice.springboot.ems.service.impl.EmployeeServiceImpl.updateEmployee(..))",
            returning = "result"
    )
    public void logEmployeeUpdate(JoinPoint joinPoint, Object result) {
        if (result instanceof EmployeeDto dto) {
            employeeLogService.logAction( dto.getId(),"UPDATE", "SYSTEM");
        }
    }

    @After("execution(* com.practice.springboot.ems.service.impl.EmployeeServiceImpl.deleteEmployee(..))")
    public void logEmployeeDelete(JoinPoint joinPoint) {
        Object arg = joinPoint.getArgs()[0];
        if (arg instanceof Long employeeId) {
            employeeLogService.logAction(employeeId,"DELETE",  "SYSTEM");
        }
    }



}
