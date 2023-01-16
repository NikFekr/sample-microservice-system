package com.modernisc.gateway;

import com.ulisesbocchio.jasyptspringboot.environment.StandardEncryptableEnvironment;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Security;
import java.util.Properties;

@SpringBootApplication
@EnableEurekaClient
@RefreshScope
public class GatewayApplication {

    private static final Logger log = LoggerFactory.getLogger(GatewayApplication.class);

    public static void main(String[] args) throws Exception{

        Security.addProvider(new BouncyCastleProvider());
        String[] modifiedArgs = new CommandLineDecryptionHandler(args).getApplicationArgs();

        SpringApplication app = new SpringApplicationBuilder()
                .environment(new StandardEncryptableEnvironment())
                .sources(GatewayApplication.class).build();

        //default properties
        Properties p = new Properties();
        p.put("spring.main.allow-bean-definition-overriding", true);
        app.setDefaultProperties(p);

        Environment env = app.run(modifiedArgs).getEnvironment();
        logApplicationStartup(env);
    }


    private static void logApplicationStartup(Environment env) {

        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null &&
                env.getProperty("server.ssl.enabled") != null &&
                env.getProperty("server.ssl.enabled").equals("true")) {
            protocol = "https";
        }

        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");

        if (StringUtils.isBlank(contextPath)) {
            contextPath = "/";
        }

        String hostAddress = "localhost";
        String hostName = "localhost";

        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }

        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}{}\n\t" +
                        "External: \t{}://{}:{}{}\n\t" +
                        "External-hostname: \t{}://{}:{}{}\n\t" +
                        "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                protocol,
                hostName,
                serverPort,
                contextPath,
                env.getActiveProfiles());

        String configServerStatus = env.getProperty("configserver.status");
        String configServerURL = env.getProperty("spring.cloud.config.uri");
        if (configServerStatus == null) {
            configServerStatus = "Not found or not setup for this application";
            configServerURL = "";
        }
        log.info("\n----------------------------------------------------------\n\t" +
                "Config Server: \t{}\n----------------------------------------------------------",
                configServerStatus + " " + configServerURL);
    }
}
