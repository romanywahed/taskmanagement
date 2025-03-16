package com.taskmanagement.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Define pointcuts (Log all public methods in services and controllers)
    @Pointcut("execution(* com.taskmanagement.service..*(..)) || execution(* com.taskmanagement.controller..*(..)) ")
    public void applicationMethods() {}

    // Around advice to log method execution details
    @Around("applicationMethods()")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // Get method details
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        logger.info("Method {} called with arguments: {}", methodName, Arrays.toString(args));

        try {
            Object result = joinPoint.proceed();  // Execute method
            long endTime = System.currentTimeMillis();
            logger.info("Method {} executed successfully in {} ms, returning: {}", methodName, (endTime - startTime), result);
            return result;
        } catch (Exception e) {
            logger.error("Exception in method {}: {}", methodName, e.getMessage(), e);
            throw e;
        }
    }
}