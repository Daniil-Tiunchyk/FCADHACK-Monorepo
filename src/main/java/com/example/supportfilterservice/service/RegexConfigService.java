package com.example.supportfilterservice.service;
import com.example.supportfilterservice.domain.DTO.FilterMode;
import com.example.supportfilterservice.domain.DTO.RegexConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegexConfigService {
    private final RedisTemplate<String, List<RegexConfig>> redisTemplate;

    public RegexConfigService(RedisTemplate<String, List<RegexConfig>> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public List<RegexConfig> getRegexConfigs() {
        List<RegexConfig> regexConfigs = redisTemplate.opsForValue().get("regexConfigs");

        if (regexConfigs != null) {
            // Сортируем список по наименьшему приоритету
            return regexConfigs.stream()
                    .sorted(Comparator.comparingInt(config -> config.getModes().stream()
                            .mapToInt(FilterMode::getPriority)
                            .min()
                            .orElse(Integer.MAX_VALUE))) // Если нет режимов, ставим максимальный приоритет
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }


}
