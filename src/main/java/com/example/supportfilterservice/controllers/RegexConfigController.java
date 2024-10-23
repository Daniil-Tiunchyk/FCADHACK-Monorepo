package com.example.supportfilterservice.controllers;

import com.example.supportfilterservice.domain.DTO.RegexConfig;
import com.example.supportfilterservice.service.RegexConfigService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regex-configs")
public class RegexConfigController {

    private final RegexConfigService regexConfigService;

    public RegexConfigController(RegexConfigService regexConfigService) {
        this.regexConfigService = regexConfigService;
    }

    // Получить все RegexConfig
    @GetMapping
    public ResponseEntity<List<RegexConfig>> getAllRegexConfigs() {
        List<RegexConfig> regexConfigs = regexConfigService.getAllRegexConfigs();
        return ResponseEntity.ok(regexConfigs);
    }


    // Создать новый RegexConfig
    @PostMapping
    public ResponseEntity<String> createRegexConfig(@RequestBody RegexConfig regexConfig) {
        try {
            regexConfigService.createRegexConfig(regexConfig);
            return ResponseEntity.status(HttpStatus.CREATED).body("RegexConfig created successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Обновить существующий RegexConfig
    @PutMapping
    public ResponseEntity<String> updateRegexConfig(
            @RequestParam String field,
            @RequestParam String pattern,
            @RequestBody RegexConfig updatedConfig) {
        try {
            regexConfigService.updateRegexConfig(field, pattern, updatedConfig);
            return ResponseEntity.ok("RegexConfig updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Удалить RegexConfig
    @DeleteMapping
    public ResponseEntity<String> deleteRegexConfig(
            @RequestParam String field,
            @RequestParam String pattern) {
        try {
            regexConfigService.deleteRegexConfig(field, pattern);
            return ResponseEntity.ok("RegexConfig deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
