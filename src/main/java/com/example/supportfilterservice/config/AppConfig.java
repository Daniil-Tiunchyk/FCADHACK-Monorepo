package com.example.supportfilterservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "filter")
@Getter
@Setter
public class AppConfig {

    private RegexConfig regex;
    private EndpointConfig endpoint;

    @Getter
    @Setter
    public static class RegexConfig {
        private boolean enabled;
        //TODO Убрать enabled
        //TODO Нужен ли field
        //TODO Нужен Mode
        private String pattern;
    }

    @Getter
    @Setter
    public static class EndpointConfig {
        private List<String> disabledEndpoints;
    }
}
