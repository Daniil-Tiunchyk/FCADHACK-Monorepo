package com.example.supportfilterservice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ConfigUtil {

    private static Environment environment;

    @Autowired
    public ConfigUtil(Environment environment) {
        ConfigUtil.environment = environment;
    }

    public static String getProperty(String key, String defaultValue) {
        return environment.getProperty(key, defaultValue);
    }

}
