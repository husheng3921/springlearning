package com.hs.demo.aop;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 定义切面
 */
@Aspect
@Component
@Slf4j
public class AspectTest {

    /**
     * 定义切点
     */
    @Pointcut("execution(public * com.hs.demo.service.impl..*.*(..))")
    public void pointcut(){}

    /**
     * 定义通知方法
     */
    @Before("pointcut()")
    public void record(){
        log.info("前置通知{}",  System.currentTimeMillis());
        //System.out.println("织入增强" + System.currentTimeMillis());
    }

    @After("pointcut()")
    public void afterRecord(){
        log.info("后置通知{}",  System.currentTimeMillis());
        //System.out.println("后置通知" + System.currentTimeMillis());
    }

    @AfterReturning("pointcut()")
    public void afterReturnRecord(){
        log.info("后置返回通知{}",  System.currentTimeMillis());
    }

    @Around("pointcut()")
    public void aroundRecord(){
        log.info("环绕通知{}",  System.currentTimeMillis());
    }
}
