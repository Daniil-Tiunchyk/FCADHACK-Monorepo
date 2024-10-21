package com.example.supportfilterservice.service;

import com.example.supportfilterservice.domain.DTO.FilterMode;
import com.example.supportfilterservice.domain.DTO.RegexConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegexConfigService {
    private final RedisTemplate<String, List<RegexConfig>> redisTemplate;
    @Qualifier("regexConfigUpdatesTopic")
    private final ChannelTopic regexConfigUpdatesTopic;

    public RegexConfigService(RedisTemplate<String, List<RegexConfig>> redisTemplate, ChannelTopic regexConfigUpdatesTopic) {
        this.redisTemplate = redisTemplate;
        this.regexConfigUpdatesTopic = regexConfigUpdatesTopic;
    }

    public List<RegexConfig> getAllRegexConfigs() {
        List<RegexConfig> regexConfigs = redisTemplate.opsForValue().get("regexConfigs");
        return regexConfigs != null ? regexConfigs : new ArrayList<>();
    }

    public void createRegexConfig(RegexConfig regexConfig) {
        List<RegexConfig> regexConfigs = getAllRegexConfigs();

        // Проверка уникальности (по полям field и pattern)
        if (regexConfigs.stream().anyMatch(r -> r.getField().equals(regexConfig.getField()) && r.getPattern().equals(regexConfig.getPattern()))) {
            throw new IllegalArgumentException("RegexConfig with this field and pattern already exists.");
        }

        regexConfigs.add(regexConfig);
        redisTemplate.opsForValue().set("regexConfigs", regexConfigs);

        // Публикуем обновление
        publishRegexConfigUpdate(regexConfigs);
    }

    public void updateRegexConfig(String field, String pattern, RegexConfig updatedConfig) {
        List<RegexConfig> regexConfigs = getAllRegexConfigs();
        RegexConfig existingConfig = regexConfigs.stream()
                .filter(r -> r.getField().equals(field) && r.getPattern().equals(pattern))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("RegexConfig not found."));

        // Проверка на уникальность нового сочетания field и pattern
        boolean isDuplicate = regexConfigs.stream()
                .anyMatch(r -> !r.equals(existingConfig) && r.getField().equals(updatedConfig.getField()) && r.getPattern().equals(updatedConfig.getPattern()));

        if (isDuplicate) {
            throw new IllegalArgumentException("RegexConfig with this new field and pattern already exists.");
        }

        // Обновляем существующий объект
        existingConfig.setEnabled(updatedConfig.isEnabled());
        existingConfig.setModes(updatedConfig.getModes());
        existingConfig.setField(updatedConfig.getField()); // Обновляем поле field
        existingConfig.setPattern(updatedConfig.getPattern()); // Обновляем поле pattern

        redisTemplate.opsForValue().set("regexConfigs", regexConfigs);

        // Публикуем обновление
        publishRegexConfigUpdate(regexConfigs);
    }

    public void deleteRegexConfig(String field, String pattern) {
        List<RegexConfig> regexConfigs = getAllRegexConfigs();
        boolean removed = regexConfigs.removeIf(r -> r.getField().equals(field) && r.getPattern().equals(pattern));

        if (!removed) {
            throw new IllegalArgumentException("RegexConfig not found.");
        }

        redisTemplate.opsForValue().set("regexConfigs", regexConfigs);

        // Публикуем обновление
        publishRegexConfigUpdate(regexConfigs);
    }

    private void publishRegexConfigUpdate(List<RegexConfig> regexConfigs) {
        redisTemplate.convertAndSend(regexConfigUpdatesTopic.getTopic(), regexConfigs);
    }

    public List<RegexConfig> getSortedRegexConfigs() {
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