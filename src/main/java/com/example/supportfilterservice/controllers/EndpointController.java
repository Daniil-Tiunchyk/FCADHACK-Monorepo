package com.example.supportfilterservice.controllers;

import com.example.supportfilterservice.domain.DTO.Endpoint;
import com.example.supportfilterservice.service.EndpointService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/endpoints")
public class EndpointController {
    private final EndpointService endpointService;

    public EndpointController(EndpointService endpointService) {
        this.endpointService = endpointService;
    }

    // Получить все endpoints
    @GetMapping
    public ResponseEntity<List<Endpoint>> getAllEndpoints() {
        List<Endpoint> endpoints = endpointService.getAllEndpoints();
        return ResponseEntity.ok(endpoints);
    }

    // Создать новый endpoint
    @PostMapping
    public ResponseEntity<String> createEndpoint(@RequestBody Endpoint endpoint) {
        if (endpoint == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Endpoint must not be null.");
        }
        try {
            endpointService.createEndpoint(endpoint);
            return ResponseEntity.status(HttpStatus.CREATED).body("Endpoint created successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Обновить существующий endpoint
    // Обновить существующий endpoint без передачи в URL
    @PutMapping
    public ResponseEntity<String> updateEndpoint(
            @RequestParam String endpointName,
            @RequestBody Endpoint endpoint) {
        if (endpointName == null || endpoint == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Endpoint name and endpoint must not be null.");
        }
        try {
            endpointService.updateEndpoint(endpointName, endpoint.isEnabled());
            return ResponseEntity.ok("Endpoint updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Удалить endpoint без передачи в URL
    @DeleteMapping
    public ResponseEntity<String> deleteEndpoint(@RequestParam String endpointName) {
        if (endpointName == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Endpoint name must not be null.");
        }
        try {
            endpointService.deleteEndpoint(endpointName);
            return ResponseEntity.ok("Endpoint deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}