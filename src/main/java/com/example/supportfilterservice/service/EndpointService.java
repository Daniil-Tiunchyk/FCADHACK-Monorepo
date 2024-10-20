package com.example.supportfilterservice.service;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EndpointService {
    private final RedisTemplate<String, List<String>> redisTemplate;

    public EndpointService(RedisTemplate<String, List<String>> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public List<String> getDisabledEndpoints() {
        return redisTemplate.opsForValue().get("disabledEndpoints");
    }
}
