package com.practice.springboot.ems.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
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


}
