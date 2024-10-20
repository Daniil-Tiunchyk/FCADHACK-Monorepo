package com.example.supportfilterservice.service;
import com.example.supportfilterservice.domain.DTO.RegexConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegexConfigService {
    private final RedisTemplate<String, List<RegexConfig>> redisTemplate;

    public RegexConfigService(RedisTemplate<String, List<RegexConfig>> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public List<RegexConfig> getRegexConfigs() {
        return redisTemplate.opsForValue().get("regexConfigs");
    }
}
