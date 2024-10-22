package com.example.supportfilterservice.config;

import com.example.supportfilterservice.domain.DTO.Endpoint;
import com.example.supportfilterservice.domain.DTO.RegexConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

import java.util.List;

@Configuration
public class RedisConfig {
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, List<Endpoint>> endpointRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, List<Endpoint>> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        return template;
    }
    @Bean
    public RedisTemplate<String, List<RegexConfig>> regexConfigRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, List<RegexConfig>> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
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
