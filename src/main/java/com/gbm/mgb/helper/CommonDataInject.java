package com.gbm.mgb.helper;

import com.gbm.mgb.domain.rbac.User;
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
 * 通用数据注入
 * @author waylon
 * @date 2017/11/2
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
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            Class spuerClass = arg.getClass().getSuperclass();
            Method setCreateDate = spuerClass.getDeclaredMethod("setCreateDate", Date.class);
            Method setUpdateDate = spuerClass.getDeclaredMethod("setUpdateDate", Date.class);
            Method setCreateUserId = spuerClass.getDeclaredMethod("setCreateUserId", String.class);
            Method setUpdateUserId = spuerClass.getDeclaredMethod("setUpdateUserId", String.class);

            // 获取session用户标示
            User user = SessionThreadLocalHelper.userThreadLocal.get();
            setCreateDate.invoke(arg,new Date());
            setUpdateDate.invoke(arg,new Date());
            setCreateUserId.invoke(arg,user.getId());
            setUpdateUserId.invoke(arg,user.getId());
            logger.info("[insert]"+arg);
        }
        Object o = pjp.proceed();
        return o;
    }

    @Around("updateCutMethod()")
    public Object doupdateAround(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            Class spuerClass = arg.getClass().getSuperclass();
            Method setUpdateDate = spuerClass.getDeclaredMethod("setUpdateDate", Date.class);
            Method setUpdateUserId = spuerClass.getDeclaredMethod("setUpdateUserId", String.class);
            // 获取session用户标示
            User user = SessionThreadLocalHelper.userThreadLocal.get();
            setUpdateDate.invoke(arg,new Date());
            setUpdateUserId.invoke(arg,user.getId());
            logger.info("[update]"+arg);
        }
        Object o = pjp.proceed();
        return o;
    }
}
