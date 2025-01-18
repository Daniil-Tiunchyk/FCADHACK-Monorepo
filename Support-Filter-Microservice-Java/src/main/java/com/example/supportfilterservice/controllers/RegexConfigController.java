package com.example.supportfilterservice.controllers;

import com.example.supportfilterservice.domain.DTO.RegexConfig;
import com.example.supportfilterservice.service.RegexConfigService;
import com.example.supportfilterservice.service.TestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regex-configs")
public class RegexConfigController {

    private final RegexConfigService regexConfigService;
    public final TestService testService;

    public RegexConfigController(RegexConfigService regexConfigService, TestService testService) {
        this.regexConfigService = regexConfigService;
        this.testService = testService;
    }

    // Получить все RegexConfig
    @GetMapping
    public ResponseEntity<List<RegexConfig>> getAllRegexConfigs() {
        List<RegexConfig> regexConfigs = regexConfigService.getAllRegexConfigs();
        return ResponseEntity.ok(regexConfigs);
    }


    // Создать новый RegexConfig
    // Создать новый RegexConfig
    @PostMapping
    public ResponseEntity<String> createRegexConfig(@RequestBody RegexConfig regexConfig) {
        if (regexConfig == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("RegexConfig must not be null.");
        }
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
        if (field == null || pattern == null || updatedConfig == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Field, pattern, and updatedConfig must not be null.");
        }
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
        if (field == null || pattern == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Field and pattern must not be null.");
        }
        try {
            regexConfigService.deleteRegexConfig(field, pattern);
            return ResponseEntity.ok("RegexConfig deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/test")
    public ResponseEntity<String> processTestRequest(@RequestBody String value) {
        if (value == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Value must not be null.");
        }
        try {
            String result = testService.test(value);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request: " + e.getMessage());
        }
    }

}
