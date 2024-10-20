package com.example.supportfilterservice.service;
import com.example.supportfilterservice.config.AppConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegexConfigService {
    private final RedisTemplate<String, List<AppConfig.RegexConfig>> redisTemplate;

    public RegexConfigService(RedisTemplate<String, List<AppConfig.RegexConfig>> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public List<AppConfig.RegexConfig> getRegexConfigs() {
        return redisTemplate.opsForValue().get("regexConfigs");
    }
}