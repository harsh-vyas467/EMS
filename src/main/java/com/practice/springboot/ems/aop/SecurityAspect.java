package com.practice.springboot.ems.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class SecurityAspect {
    @Before("execution(* com.practice.springboot.ems.controller.*.*(..))")
    public void checkSecurity(JoinPoint joinPoint) {
        // Simulate a security check
        log.info("Security check passed for method: {}", joinPoint.getSignature());
        // You can also throw exception if access denied
    }
}
