package com.modernisc.configurationservice;

import com.ulisesbocchio.jasyptspringboot.environment.StandardEncryptableEnvironment;
import io.micrometer.core.instrument.util.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Security;
import java.util.Properties;

@SpringBootApplication
@EnableConfigServer
@RefreshScope
public class ConfigurationServiceApplication {

    private static final Logger log = LoggerFactory.getLogger(ConfigurationServiceApplication.class);

    public static void main(String[] args) throws Exception{

        Security.addProvider(new BouncyCastleProvider());
        String[] modifiedArgs = new CommandLineDecryptionHandler(args).getApplicationArgs();

        SpringApplication app = new SpringApplicationBuilder()
                .environment(new StandardEncryptableEnvironment())
                .sources(ConfigurationServiceApplication.class).build();
        app.setWebApplicationType(WebApplicationType.SERVLET);

        //default properties
        Properties p = new Properties();
        p.put("spring.main.allow-bean-definition-overriding", true);
        app.setDefaultProperties(p);

//        SpringApplication app = new SpringApplication(ConfigurationServiceApplication.class);
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

        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }

        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}{}\n\t" +
                        "External: \t{}://{}:{}{}\n\t" +
                        "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                env.getActiveProfiles());

        String configServerStatus = env.getProperty("configserver.status");
        if (configServerStatus == null) {
            configServerStatus = "Not found or not setup for this application";
        }
        log.info("\n----------------------------------------------------------\n\t" +
                "Config Server: \t{}\n----------------------------------------------------------", configServerStatus);

        log.info("\n----------------------------------------------------------\n\t" +
                        "Search Location: \t{}\n----------------------------------------------------------",
                env.getProperty("spring.cloud.config.server.native.search-locations"));
    }
}
