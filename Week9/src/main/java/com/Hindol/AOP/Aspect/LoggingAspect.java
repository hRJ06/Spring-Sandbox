package com.Hindol.AOP.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class LoggingAspect {

    /* Before kind pointcut */
    /* @Before("execution(* orderPackage(..))") */
    /* @Before("execution(* com.Hindol.AOP.Service.Implementation.*.orderPackage(..))") */
    @Before("execution(* com.Hindol.AOP.Service.Implementation.*.*(..))")
    public void beforeOrderPackage(JoinPoint joinPoint) {
        log.info("Before called in Logging Aspect, Kind - {}", joinPoint.getKind());
        log.info("Before called in Logging Aspect, Signature - {}", joinPoint.getSignature());
    }

    /* Within kind pointcut */
    @Before("within(com.Hindol.AOP.Service.Implementation.*)") /* ..* -> Recursively call any package, subpackage and their class */
    public void beforeImplementationCall() {
        log.info("Service Implementation called");
    }

    /* Annotation kind pointcut */
    @Before("@annotation(jakarta.transaction.Transactional)") /* Use && or || with pointcut */
    public void beforeTransactionalAnnotationCalls() {
        log.info("Before Transactional Annotation Call");
    }

    /* @Before("@annotation(com.Hindol.AOP.Aspect.LogAnnotation)") */
    @Before("logAnnotationPointcut()")
    public void beforeLogAnnotationCall() {
        log.info("Before Log Annotation Call");
    }

    @After("logAnnotationPointcut()")
    public void afterLogAnnotationCall() {
        log.info("After Log Annotation Call");
    }

    /* @After("execution(* orderPackage(..))") */
    /*
        @After("execution(* com.Hindol.AOP.Service.Implementation.*.*(..))")
        public void afterOrderPackage(JoinPoint joinPoint) {
            log.info("After called in Logging Aspect, Kind - {}", joinPoint.getKind());
            log.info("After called in Logging Aspect, Signature - {}", joinPoint.getSignature());
        }
    */

    /* Declare Pointcut */
    @Pointcut("@annotation(com.Hindol.AOP.Aspect.LogAnnotation)")
    public void logAnnotationPointcut() {}
}
