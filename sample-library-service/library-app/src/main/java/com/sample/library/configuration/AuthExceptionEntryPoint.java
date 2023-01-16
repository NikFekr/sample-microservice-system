package com.sample.library.configuration;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

//        ServiceGatewayError serviceGatewayError = new ServiceGatewayError(StaticErrorCodes.ERROR_USER_UNAUTHORIZED);

        throw new ServiceGatewayError(StaticErrorCodes.ERROR_USER_UNAUTHORIZED);

    }
}
