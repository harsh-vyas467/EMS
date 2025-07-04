package com.practice.springboot.ems.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ValidationAspect {
    @Before("execution(* com.practice.springboot.ems.service.EmployeeService.createEmployee(..))")
    public void validateCreateEmployee(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        // Simple validation simulation
        if (args != null && args.length > 0) {
            log.info("Validation passed for employee: {}", args[0]);
        }
    }
}
