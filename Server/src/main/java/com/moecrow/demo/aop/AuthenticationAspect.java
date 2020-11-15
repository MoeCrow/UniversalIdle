package com.moecrow.demo.aop;

import com.moecrow.demo.commons.UserSession;
import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author willz
 * @date 2020.11.12
 */
@Aspect
@Log
@Component
public class AuthenticationAspect {
    @Pointcut("execution(public * com.moecrow.demo.controller.WsController.*(..)) " +
            "&& @annotation(org.springframework.messaging.handler.annotation.MessageMapping)")
    public void pointCut(){}

    @Autowired
    private UserSession userSession;

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
//        log.info("aop:" + userSession.toString());
        Object result = pjp.proceed();
        return result;
    }
}
