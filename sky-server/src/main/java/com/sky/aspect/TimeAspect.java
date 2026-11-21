package com.sky.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TimeAspect {

    @Around("@annotation(com.sky.annotation.TimeAnno)")
    public Object getRunTime(ProceedingJoinPoint jP) throws Throwable {
        Long startTime = System.currentTimeMillis();
        Object result = jP.proceed();
        Long endTime = System.currentTimeMillis();
        log.info("运行时长{}",endTime-startTime);
        return result;
    }


}
