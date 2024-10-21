package com.example.dataingestionservice.controller;

import com.example.dataingestionservice.service.SupportRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/support-requests")
public class SupportRequestController {

    private final SupportRequestService supportRequestService;

    public SupportRequestController(SupportRequestService supportRequestService) {
        this.supportRequestService = supportRequestService;
    }

    @PostMapping
    public ResponseEntity<String> createSupportRequest(@RequestBody String request) {
        supportRequestService.sendSupportRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Support request sent to Proxy-Service.");
    }
}