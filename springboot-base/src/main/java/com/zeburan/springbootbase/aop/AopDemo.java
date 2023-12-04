package com.zeburan.springbootbase.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Create by swtywang on 11/4/23 7:53 PM
 */
@Aspect
@Component
@Slf4j
public class AopDemo {

    @Pointcut("execution(* com.zeburan.springbootbase..service.*.*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void beginTransaction() {
        log.info("AOP.... before beginTransaction");
    }

    @After("pointcut()")
    public void commit() {
        log.info("AOP.... after commit");
    }

    @AfterReturning(pointcut = "pointcut()", returning = "returnObject")
    public void afterReturning(JoinPoint joinPoint, Object returnObject) {
        log.info("AOP.... afterReturning");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        log.info("AOP.... afterThrowing afterThrowing  rollback");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("AOP.... around");
            return joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        } finally {
            log.info("AOP.... around");
        }
    }
}
