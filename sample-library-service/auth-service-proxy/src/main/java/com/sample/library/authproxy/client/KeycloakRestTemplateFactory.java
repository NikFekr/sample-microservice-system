package com.sample.library.authproxy.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Esmaeil NikFekr on 5/29/21.
 */
@Configuration
public class KeycloakRestTemplateFactory {

    @Value("${keycloak.credentials.secret}")
    public String secret;

    @Value("${keycloak.resource}")
    public String resource;


    @Bean
    public RestTemplate keycloakLoginRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(new KeycloakSecurityInterceptor(resource, secret));
        restTemplate.setInterceptors(interceptors);
        restTemplate.setRequestFactory(new ClientRequestFactoryWithEntity());

        return restTemplate;
    }
}
