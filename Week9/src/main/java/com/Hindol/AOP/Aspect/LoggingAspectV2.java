package com.Hindol.AOP.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspectV2 {
    @Before("allServiceMethodPointCut()")
    public void beforeServiceMethodCall(JoinPoint joinPoint) {
        log.info("Before Service Method Call, {}", joinPoint.getSignature());
    }

    /*
        @After("allServiceMethodPointCut()")
        public void afterServiceMethodCall(JoinPoint joinPoint) {
            log.info("After Service Method Call, {}", joinPoint.getSignature());
        }
    */

    @AfterReturning(value = "allServiceMethodPointCut()", returning = "returnedObj")
    public void afterServiceMethodCall(JoinPoint joinPoint, Object returnedObj) {
        log.info("After Service Method Call, {}", joinPoint.getSignature());
        log.info("Return Object : {}", returnedObj);
    }

    @AfterThrowing(value = "allServiceMethodPointCut()")
    public void afterServiceMethodException(JoinPoint joinPoint) {
        log.info("After Service Method Exception, {}", joinPoint.getSignature());
    }

    @Around("allServiceMethodPointCut()")
    public Object logExecutionTime(ProceedingJoinPoint point) throws Throwable {
        Long startTime = System.currentTimeMillis();
        Object returnedValue = point.proceed();
        Long endTime = System.currentTimeMillis();
        Long diff = endTime - startTime;
        log.info("Time taken for {} is {}", point.getSignature(), diff);
        return returnedValue;
    }


    @Pointcut("execution(* com.Hindol.AOP.Service.Implementation.*.*(..))")
    public void allServiceMethodPointCut() {

    }
}
