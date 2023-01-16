package com.sample.library.authproxy.client;

import com.sample.library.authproxy.datatransferobject.LoginRequest;
import com.sample.library.authproxy.datatransferobject.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class KeycloakRestTemplateClient {


    @Value("${oath2-token-path}")
    private String tokenPath;

    private final RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(KeycloakRestTemplateClient.class);


    @Autowired
    public KeycloakRestTemplateClient(@Qualifier("keycloakLoginRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public LoginResponse getToken(LoginRequest loginRequest) {
        return post(tokenPath, loginRequest, LoginResponse.class);
    }


    private <T> T post(String path, LoginRequest loginRequest, Class<T> responseType) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", loginRequest.getGrantType());
        map.add("username", loginRequest.getUsername());
        map.add("password", loginRequest.getPassword());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        T body;

        try {

            body = restTemplate.exchange(
                    path,
                    HttpMethod.POST,
                    entity,
                    responseType
            ).getBody();

        } catch (RestClientException exp) {
            LOGGER.error("Error in calling keycloak: {}", exp.getMessage());
            throw exp;
        }

        return body;
    }
}
