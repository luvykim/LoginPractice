package com.example.demo.common.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerfAspect {

    @Around("@annotation(PerLogging)")
    public Object logPerf(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.currentTimeMillis();
        System.out.println(System.currentTimeMillis() - begin + "ms");
        Object retVal = pjp.proceed();
        System.out.println(System.currentTimeMillis() - begin + "ms");
        return retVal;
    }

    @After("@annotation(AfterLogging)")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("<<   After   >>");
    }

    @Before("@annotation(BeforeLogging)")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("<<   Before   >>");
    }

    @AfterReturning("@annotation(AfterReturnLogging)")
    public void logAfterReturn(JoinPoint joinPoint) {
        System.out.println("<<   AfterReturning   >>");
    }

    @AfterThrowing("@annotation(AfterThrowLogging)")
    public void logThrowReturn(JoinPoint joinPoint) {
        System.out.println("<<   @AfterThrowing   >>");
    }
}
