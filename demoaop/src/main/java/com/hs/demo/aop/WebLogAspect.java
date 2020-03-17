package com.hs.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 定义控制器日志操作类
 */
@Aspect
//@Component
@Slf4j
public class WebLogAspect {

    /**
     * 定义切点
     */
    @Pointcut("execution(public * com.hs.demo.controller..*.*(..))")
    public void pointcut(){}

    /**
     * 定义通知
     */
    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        log.info("进入doBefore");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //记录日志
        log.info("访问开始{}", System.currentTimeMillis());
        log.info("URL {}", request.getRequestURL());
        log.info("HTTP_METHOD {}", request.getMethod());
        log.info("IP{}", request.getRemoteAddr());
        log.info("CLASS_METHOD{}.{}", joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName());
        log.info("Args{}", Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning(returning = "ret", pointcut = "pointcut()")
    public void doAfter(Object ret) throws Throwable{
        //访问结束
        log.info("访问结束{}", System.currentTimeMillis());
    }
}
