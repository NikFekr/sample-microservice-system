package com.sample.library.authproxy.service.impl;

import com.sample.library.authproxy.client.KeycloakRestTemplateClient;
import com.sample.library.authproxy.domainvalue.GrantType;
import com.sample.library.authproxy.service.AuthService;
import com.sample.library.authproxy.datatransferobject.LoginRequest;
import com.sample.library.authproxy.datatransferobject.LoginResponse;

/**
 * @author Esmaeil NikFekr on 18.12.22
 */
public class AuthServiceImpl implements AuthService {

    private final KeycloakRestTemplateClient restTemplate;


    public AuthServiceImpl(KeycloakRestTemplateClient restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        loginRequest.setGrantType(GrantType.PASSWORD.getGrantType());
        return restTemplate.getToken(loginRequest);
    }
}
