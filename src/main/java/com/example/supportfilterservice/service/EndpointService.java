package com.example.supportfilterservice.service;
import com.example.supportfilterservice.domain.DTO.Endpoint;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EndpointService {
    private final RedisTemplate<String, List<Endpoint>> redisTemplate;

    public EndpointService(RedisTemplate<String, List<Endpoint>> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public List<Endpoint> getDisabledEndpoints() {
        return redisTemplate.opsForValue().get("disabledEndpoints");
    }
}
