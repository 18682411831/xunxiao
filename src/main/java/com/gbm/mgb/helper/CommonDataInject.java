package com.gbm.mgb.helper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by Waylon on 2017/10/22.
 */
@Aspect
@Component
public class CommonDataInject {
    private final Logger logger = LoggerFactory.getLogger(CommonDataInject.class);

    @Pointcut("execution(* com.gbm.mgb.domain..*Repository.insert*(..))")
    private void insertCutMethod() {

    }

    @Pointcut("execution(* com.gbm.mgb..*.domain.*Repository.update*(..))")
    private void updateCutMethod() {
    }

    @Around("insertCutMethod()")
    public Object doInsertAround(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("123123123");
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            Class spuerClass = arg.getClass().getSuperclass();
            Method setCreateDate = spuerClass.getDeclaredMethod("setCreateDate", Date.class);
            Method setUpdateDate = spuerClass.getDeclaredMethod("setUpdateDate", Date.class);
            setCreateDate.invoke(arg,new Date());
            setUpdateDate.invoke(arg,new Date());
            logger.info("[insert]"+arg);
        }
        Object o = pjp.proceed();
        return o;
    }

    @Around("updateCutMethod()")
    public Object doupdateAround(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            logger.debug("[update]"+arg);
        }
        Object o = pjp.proceed();
        return o;
    }
}
