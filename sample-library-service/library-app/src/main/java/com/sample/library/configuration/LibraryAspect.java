package com.sample.library.configuration;

import com.sample.library.service.AuditLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * @author Esmaeil NikFekr on 9/12/20.
 */
@Aspect
@Configuration
public class LibraryAspect {

    @Around(
            "execution(* com.sample.library.api.mapper.ImageResultMapper.*(..)) || " +
            "execution(* com.sample.library.api.mapper.PersonInfoRequestMapper.*(..)) || " +
            "execution(* com.sample.library.api.mapper.PersonInfoResultMapper.*(..)) || " +
            "execution(* com.sample.library.SabtAhvalSOAPProxy.*(..)) || " +
            "execution(* com.sample.library.api.consumerone.UIAPI.*(..)) || " +
            "execution(* com.sample.library.api.impl.UIAPIWSImpl.*(..)) || " +
            "execution(* com.sample.library.service.SabtAhvalService.*(..)) " +
            "")
    public Object serviceGatewayLog(ProceedingJoinPoint joinPoint) throws Throwable {
        return auditLog().doLogging(joinPoint);
    }

    @Bean
    public AuditLog auditLog() {
        AuditLog auditLog = new AuditLog();
        auditLog.setLogTypes(new ArrayList<String>(){{
            add("login");
            add("send");
            add("verify");
            add("act");
            add("deact");
            add("get");
            add("sms");
            add("app");
        }});

        return auditLog;
    }
}
