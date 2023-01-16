package com.sample.library.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Value("${springdoc.oAuthFlow.authorization-url}")
    private String authorizationUrl;

    @Value("${springdoc.oAuthFlow.token-url}")
    private String tokenUrl;

    @Bean
    public OpenAPI springShopOpenAPI(@Value("${application.version}") String appVersion) {


        OAuthFlow oAuthFlow = new OAuthFlow();
        oAuthFlow.authorizationUrl(authorizationUrl);
        oAuthFlow.tokenUrl(tokenUrl);

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("security_auth", new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(new OAuthFlows().password(oAuthFlow)))
                )
                .info(new Info().title("Service Gateway")
                        .description("This service works as a service gateway for calling external services")
                        .version(appVersion)
                        .license(new License().name("Apache 2.0").url("https://www.modernisc.com")));
    }
}
