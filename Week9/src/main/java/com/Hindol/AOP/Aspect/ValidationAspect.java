package com.Hindol.AOP.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//@Aspect
@Component
@Slf4j
public class ValidationAspect {
    @Pointcut("execution(* com.Hindol.AOP.Service.Implementation.*.*(..))")
    public void allServiceMethodPointCut() {}

    @Around("allServiceMethodPointCut()")
    public Object validateOrderId(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        Long orderId = (Long) args[0];
        if(orderId > 0) return point.proceed();
        return "Cannot call with -ve Order ID";
    }
}
