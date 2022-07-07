package com.example.demo.common.annotation;

import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.example.demo.common.Role;
import com.example.demo.service.MemberService;

@Component
@Aspect
public class PerfAspect {
    @Autowired
    MemberService memberService;

    Role role;

    @Around("@annotation(PerLogging)")
    public Object logPerf(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.currentTimeMillis();
        System.out.println(System.currentTimeMillis() - begin + "ms");
        Object retVal = pjp.proceed();
        System.out.println(System.currentTimeMillis() - begin + "ms");
        return retVal;
    }

    @Around("@annotation(Auth)")
    public Object authCheck(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest req =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        String userRole = memberService.getRole((String) req.getSession().getAttribute("username"));
        System.out.println((String) req.getSession().getAttribute("username"));
        if (userRole == null) {
            System.out.println("401 Unauthorized - role doesn't exist exception");
            return new RuntimeException();
        }
        // Role 확인
        if (!userRole.equalsIgnoreCase(role.ADMIN.toString())) {
            System.out.println("401 Unauthorized - role doesn't have authority");
            return new RuntimeException();
        }
        Object retVal = pjp.proceed();
        System.out.println("--- auth approved! ---");
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
