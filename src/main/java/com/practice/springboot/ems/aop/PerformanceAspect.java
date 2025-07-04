package com.practice.springboot.ems.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class PerformanceAspect {
    @Around("execution(* com.practice.springboot.ems.service..*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed(); // method execution

        long duration = System.currentTimeMillis() - start;
        log.info("Execution time for {}: {} ms", joinPoint.getSignature(), duration);
        return result;
    }
}
