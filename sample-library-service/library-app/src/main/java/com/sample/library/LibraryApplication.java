package com.sample.library;

import com.sample.library.configuration.CommandLineDecryptionHandler;
import com.sample.library.utility.StringUtility;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@ComponentScan({"com.sample.library.*"})
//@RefreshScope
//@EnableEurekaClient
public class LibraryApplication {

    private static final Logger log = LoggerFactory.getLogger(LibraryApplication.class);
    private static final String SPRING_PROFILE_DEFAULT = "spring.profiles.default";
    private static final String SPRING_PROFILE_DEVELOPMENT = "dev";

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        String[] modifiedArgs = new CommandLineDecryptionHandler(args).getApplicationArgs();

        SpringApplication app = new SpringApplication(LibraryApplication.class);
        addDefaultProfile(app);
        Environment env = app.run(modifiedArgs).getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null &&
                env.getProperty("server.ssl.enabled") != null &&
                "true".equals(env.getProperty("server.ssl.enabled"))) {
            protocol = "https";
        }
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (StringUtility.isNullOrEmpty(contextPath)) {
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
        String configServerURL = env.getProperty("spring.cloud.config.uri");
        if (configServerStatus == null) {
            configServerStatus = "Not found or not setup for this application";
            configServerURL = "";
        }
        log.info("\n----------------------------------------------------------\n\t" +
                        "Config Server: \t{}\n----------------------------------------------------------",
                configServerStatus + " " + configServerURL);
    }

    /**
     * Set a default to use when no profile is configured.
     *
     * @param app the Spring application
     */
    public static void addDefaultProfile(SpringApplication app) {
        Map<String, Object> defProperties = new HashMap<>();
        defProperties.put(SPRING_PROFILE_DEFAULT, SPRING_PROFILE_DEVELOPMENT);
        app.setDefaultProperties(defProperties);
    }
}
