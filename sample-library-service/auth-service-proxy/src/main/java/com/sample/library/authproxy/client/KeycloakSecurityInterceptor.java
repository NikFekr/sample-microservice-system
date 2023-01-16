package com.sample.library.authproxy.client;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author Esmaeil NikFekr on 5/29/21.
 */
public class KeycloakSecurityInterceptor implements ClientHttpRequestInterceptor {

    public static final String AUTH_URL = "/auth";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BASIC_AUTHORIZATION = "Basic ";
    private String clientUsername;
    private String clientPassword;

    public KeycloakSecurityInterceptor(String clientUsername, String clientPassword) {
        this.clientUsername = clientUsername;
        this.clientPassword = clientPassword;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        ClientHttpResponse response;

        if (request.getURI().getPath().contains(AUTH_URL)) {

            request.getHeaders().add(AUTHORIZATION, getAuthHeader());

        }
        response = execution.execute(request, body);

        return response;

    }

    private String getAuthHeader() {
        String credentials = clientUsername + ":" + clientPassword;
        return BASIC_AUTHORIZATION + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }
}
