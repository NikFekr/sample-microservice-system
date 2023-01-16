package com.sample.library.serviceapi.impl;

import com.sample.library.LibraryApplicationTests;
import com.sample.library.api.consumerone.datatransferobject.LoginRequest;
import com.sample.library.api.consumerone.datatransferobject.LoginResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Esmaeil NikFekr on 5/15/21.
 */
public class LibraryImplTest extends LibraryApplicationTests {

    @Value("${service-gateway.ui-version}")
    private String apiVersion;

    @Value("${service-gateway.login.username}")
    private String username;

    @Value("${service-gateway.login.password}")
    private String password;


    @Test
    public void login_loginByCredentials_loginSuccessfully() {
        LoginResponse result = login();

        assertThat(result, notNullValue());
        assertThat(result.getAccessToken(), notNullValue());

    }


    private LoginResponse login() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        var result = restTemplate.postForEntity("/login", loginRequest, LoginResponse.class);
        return result.getBody();
    }
}
