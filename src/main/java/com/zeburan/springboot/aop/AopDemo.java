package com.zeburan.springboot.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Create by swtywang on 11/4/23 7:53 PM
 */
@Aspect
@Component
public class AopDemo {

    @Pointcut("execution(* com.zeburan.springboot..service.*.*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void beginTransaction() {
        System.out.println("AOP.... before beginTransaction");
    }

    @After("pointcut()")
    public void commit() {
        System.out.println("AOP.... after commit");
    }

    @AfterReturning(pointcut = "pointcut()", returning = "returnObject")
    public void afterReturning(JoinPoint joinPoint, Object returnObject) {
        System.out.println("AOP.... afterReturning");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        System.out.println("AOP.... afterThrowing afterThrowing  rollback");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            System.out.println("AOP.... around");
            return joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        } finally {
            System.out.println("AOP.... around");
        }
    }
}
