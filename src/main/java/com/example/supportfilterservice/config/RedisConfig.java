package com.example.supportfilterservice.config;

import com.example.supportfilterservice.domain.DTO.RegexConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

import java.util.List;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, List<RegexConfig>> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, List<RegexConfig>> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
    @Bean
    public ChannelTopic disabledEndpointsUpdatesTopic() {
        return new ChannelTopic("disabledEndpointsUpdates"); // Название канала
    }

    @Bean
    public ChannelTopic regexConfigUpdatesTopic() {
        return new ChannelTopic("regexConfigUpdates"); // Название канала для regexConfig
    }
}
