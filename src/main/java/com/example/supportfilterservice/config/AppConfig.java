package com.example.supportfilterservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

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
        private String field;
        private Set<FilterMode> modes = EnumSet.noneOf(FilterMode.class); // Моды фильтрации
        private String pattern;
        public boolean isModeActive(FilterMode mode) {
            return this.modes.contains(mode);
        }
    }


    public enum FilterMode {
        HIDE_DATA,      // Скрывать данные
        REMOVE_FIELD,   // Удалять поля
        REMOVE_OBJECT;  // Удалять объект

    }

    @Getter
    @Setter
    public static class EndpointConfig {
        private List<String> disabledEndpoints;
    }
}
