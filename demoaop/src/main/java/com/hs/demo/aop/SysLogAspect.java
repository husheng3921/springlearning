package com.hs.demo.aop;

import com.google.gson.Gson;
import com.hs.demo.anno.SysLog;
import com.hs.demo.util.log.SysLogBO;
import com.hs.demo.util.log.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private SysLogService logService;

    /**
     * 这里通过注解的形式，
     * 我们也可以通过切点表达式
     * execution 拦截包、类、方法
     */
    @Pointcut("@annotation(com.hs.demo.anno.SysLog)")
    public void pointcut(){}

    /**
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point)throws Throwable{
        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();
        long time = System.currentTimeMillis() - beginTime;
        try{
            saveLog(point, time);
        }catch (Exception e){

        }
        return result;
    }

    /**
     *
     * @param joinPoint
     * @param time
     */
    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLogBO sysLogBO = new SysLogBO();
        sysLogBO.setExeuTime(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        sysLogBO.setCreateDate(dateFormat.format(new Date()));
        SysLog sysLog = method.getAnnotation(SysLog.class);
        if(sysLog != null){
            //注解上的描述
            sysLogBO.setRemark(sysLog.value());
        }
        //请求的 类名、方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLogBO.setClassName(className);
        sysLogBO.setMethodName(methodName);
        //请求的参数
        Object[] args = joinPoint.getArgs();
        try{
            List<String> list = new ArrayList<String>();
            for (Object o : args) {
                list.add(new Gson().toJson(o));
            }
            sysLogBO.setParams(list.toString());
        }catch (Exception e){ }
        logService.save(sysLogBO);
    }

}
