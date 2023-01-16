package com.sample.library.configuration;

import com.moderinisc.servicegateway.common.ServiceGatewayFaultFactory;
import com.moderinisc.servicegateway.common.StaticErrorCodes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

/**
 * @author Esmaeil NikFekr on 6/7/21.
 */
@Configuration
public class LibraryContext {

    public static final String COMPONENT = "service-gateway";

    private Map<String, String> convertBundleToMap(ResourceBundle rb) {
        Map<String, String> map = new HashMap<>();

        Enumeration<String> keys = rb.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            map.put(key, rb.getString(key));
        }

        return map;
    }

    @Bean
    public ServiceGatewayFaultFactory serviceGatewayErrorStatic() {
        ResourceBundle faBundle = ResourceBundle.getBundle("service-gateway-errors", new Locale("fa", ""));
        ResourceBundle enBundle = ResourceBundle.getBundle("service-gateway-errors", Locale.ENGLISH);

        HashMap<String, Map<String, String>> map = new HashMap<>() {{
            put("en", convertBundleToMap(enBundle));
            put("fa", convertBundleToMap(faBundle));
        }};

        return new ServiceGatewayFaultFactory(map, "fa", StaticErrorCodes.class, COMPONENT);
    }
}
