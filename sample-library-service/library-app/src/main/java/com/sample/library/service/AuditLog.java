package com.sample.library.service;

import com.sample.library.datatransferobject.LogObject;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class AuditLog {
    private final Log LOG = LogFactory.getLog(this.getClass());

    private static final String dot = ".";
    private static final String comma = ",";
    private static final String paramSeparator = ":";
    private static final String nullValue = ",null";
    private static final String Id_NULL = "Id:NULL";
    private static final String facade = "Facade";
    private static final String prefix = "SERVICE_GATEWAY_";
    private static final String securityError = "Security identifier not found";
    private static final String no_message = "no message";
    private static final double round = 1000;
    private static final int returnValMaxSize = 20;

    private List<String> logTypes;

    private final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSSSSS");

    public void setLogTypes(List<String> logTypes) {
        this.logTypes = logTypes;
    }

    public Object doLogging(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object retVal;
        LogSpecification logSpecification = getLogSpecification(pjp);

        try {

            setThreadName(start,
                    logSpecification.getMethodName(),
                    logSpecification.getClassName()
            );

            logBefore(logSpecification, start);

            retVal = pjp.proceed();

            String retValString = getReturnValue(retVal);

            logAfter(logSpecification, start, retValString);

        } catch (Throwable ex) {
            logInExceptionCase(logSpecification, start, ex);
            throw ex;
        }
        return retVal;
    }

    private String getReturnValue(Object retVal) {
        String retValString = "";
        if (retVal != null) {
            retValString = retVal.getClass().getSimpleName();

            if (retVal instanceof List) {
                int size = ((List) retVal).size();
                if (size > returnValMaxSize) {
                    List subListOfRetVal = ((List) retVal).subList(0, returnValMaxSize);
                    retValString += paramSeparator + subListOfRetVal.toString() + "(size of returnVal = " + size + ")";

                } else {
                    retValString += paramSeparator + retVal.toString();
                }
            } else {
                retValString += paramSeparator + retVal.toString();
            }

        }
        return retValString;
    }

    private void logBefore(LogSpecification logSpec, long start) {

        if (isLogPermitted(logSpec.getMethodName(), logSpec.getClassName())) {
            LogObject logObject = LogObject.builder()
                    .key(Thread.currentThread().getName())
                    .start(start)
                    .user(logSpec.getUserIdentifier())
                    .clazz(logSpec.getClassName())
                    .method(logSpec.getMethodName())
                    .parameters(logSpec.getParameters())
                    .build();

            LOG.info(logObject);
        }
    }

    private void logAfter(LogSpecification logSpec, long start, String retValString) {

        long end = System.currentTimeMillis();

        if (isLogPermitted(logSpec.getMethodName(), logSpec.getClassName())) {

            LogObject logObject = LogObject.builder()
                    .key(Thread.currentThread().getName())
                    .start(start)
                    .user(logSpec.getUserIdentifier())
                    .clazz(logSpec.getClassName())
                    .method(logSpec.getMethodName())
                    .parameters(logSpec.getParameters())
                    .returnValue(retValString)
                    .end(end)
                    .duration(end - start)
                    .build();

            LOG.info(logObject);

            if (end - start > 10000)
                LOG.warn(logObject);
        }
    }

    private void logInExceptionCase(LogSpecification logSpec, long start, Throwable ex) {

        long end = System.currentTimeMillis();

        LogObject logObject = LogObject.builder()
                .key(Thread.currentThread().getName())
                .start(System.currentTimeMillis())
                .user(logSpec.getUserIdentifier())
                .clazz(logSpec.getClassName())
                .method(logSpec.getMethodName())
                .parameters(logSpec.getParameters())
                .returnValue(
                        ex.getClass().getSimpleName()
                                .concat(paramSeparator)
                                .concat(ex.getMessage() != null ? ex.getMessage() : no_message)
                )
                .end(System.currentTimeMillis())
                .duration(end - start)
                .build();

        LOG.error(logObject, ex);
    }

    private LogSpecification getLogSpecification(ProceedingJoinPoint pjp) {

        String methodName = pjp.getSignature().getName();
        String userIdentifier = getUserIdentifier();
        String className = pjp.getSignature().getDeclaringTypeName();
        String parameters = getParameters(pjp);

        return LogSpecification.builder()
                .methodName(methodName)
                .className(className)
                .parameters(parameters)
                .userIdentifier(userIdentifier)
                .build();
    }

    private boolean isLogPermitted(String methodName, String className) {
        return this.writeLog(methodName) || !className.contains(facade);
    }

    private void setThreadName(long start, String methodName, String className) {
        if (!Thread.currentThread().getName().startsWith(prefix) || className.contains(facade)) {
            String name = prefix.concat(className)
                    .concat(dot)
                    .concat(methodName)
                    .concat(dot)
                    .concat(String.valueOf(start))
                    .concat(String.valueOf(new BigDecimal(Math.random() * round).intValue()));

            Thread.currentThread().setName(name);
        }
    }

    private String getParameters(ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        StringBuilder argsString = new StringBuilder();

        if (args.length > 0) {
            for (Object arg : args) {
                if (arg != null)
                    argsString.append(comma)
                            .append(arg.getClass().getSimpleName())
                            .append(paramSeparator)
                            .append(arg.toString());
                else
                    argsString.append(nullValue);
            }
            argsString = new StringBuilder(argsString.substring(1));

        }
        return argsString.toString();
    }

    private String getUserIdentifier() {
        String userIdentifier;
        try {
            userIdentifier = getUserIdentifierDetails();

        } catch (SecurityException e) {
            LOG.info(securityError, e);
            userIdentifier = "";
        }
        return userIdentifier;
    }

    private String getUserIdentifierDetails() {
        Authentication authentication = getAuthentication();

        String principal = "";
        if (Objects.nonNull(authentication)) {
            principal = "" + authentication.getPrincipal();
        }

        return new StringJoiner(" - ", "[", "]")
                .add(principal)
                .toString();
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private boolean writeLog(String methodName) {
        if (this.logTypes != null && !this.logTypes.isEmpty()) {
            for (String type : this.logTypes)
                if (methodName.startsWith(type))
                    return true;

            return false;
        }
        return true;
    }

    @Builder
    @Data
    private static class LogSpecification {
        private String methodName;
        private String userIdentifier;
        private String className;
        private String parameters;
    }
}
