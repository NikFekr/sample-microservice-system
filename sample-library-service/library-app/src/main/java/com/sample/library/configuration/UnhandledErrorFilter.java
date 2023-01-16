package com.sample.library.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moderinisc.servicegateway.common.ServiceGatewayError;
import com.moderinisc.servicegateway.common.StaticErrorCodes;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(2)
public class UnhandledErrorFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(UnhandledErrorFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String host = httpServletRequest.getHeader("X-Forwarded-For");
        if (host == null || Strings.isEmpty(host.trim())) {
            host = servletRequest.getRemoteAddr();
        }
        ThreadContext.put("host", host);

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception exc) {
            ServiceGatewayError serviceGatewayError = null;

            if (exc instanceof ServiceGatewayError) {
                serviceGatewayError = (ServiceGatewayError) exc;

            } else if (exc.getCause() instanceof ServiceGatewayError) {
                serviceGatewayError = (ServiceGatewayError) exc.getCause();

            } else if (exc.getCause() instanceof AccessDeniedException) {
                serviceGatewayError = new ServiceGatewayError(StaticErrorCodes.ERROR_USER_UNAUTHORIZED);

            } else {
                serviceGatewayError = new ServiceGatewayError(StaticErrorCodes.ERROR_SERVICE_GATEWAY_INTERNAL,
                        exc.getClass().getSimpleName(), exc.getMessage());
            }

            logger.error(exc.getMessage(), exc);

            final ObjectMapper mapper = new ObjectMapper();

            servletResponse.setContentType(MediaType.APPLICATION_JSON.toString());
            ((HttpServletResponse) servletResponse).setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            mapper.writeValue(servletResponse.getOutputStream(), serviceGatewayError.getFaultInfo());
        } finally {
            ThreadContext.remove("host");
        }
    }

    @Override
    public void destroy() {

    }

}
