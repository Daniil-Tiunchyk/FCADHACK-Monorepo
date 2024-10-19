package com.example.supportservice.web.controller;

import com.example.supportservice.domain.entity.SupportMessage;
import com.example.supportservice.service.SupportMessageService;
import com.example.supportservice.web.dto.SupportMessageFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/support-messages")
public class SupportMessageController {

    private final SupportMessageService service;

    public SupportMessageController(SupportMessageService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public SupportMessage getMessageById(@PathVariable Long id) {
        return service.getMessageById(id);
    }

    @GetMapping
    public Page<SupportMessage> getMessages(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timestampFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timestampTo,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String supportLevel,
            @RequestParam(required = false) String messageContent,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String endpoint,
            @RequestParam(required = false) String login,
            Pageable pageable
    ) {
        SupportMessageFilter filter = SupportMessageFilter.builder()
                .timestampFrom(timestampFrom)
                .timestampTo(timestampTo)
                .userId(userId)
                .supportLevel(supportLevel)
                .messageContent(messageContent)
                .email(email)
                .endpoint(endpoint)
                .login(login)
                .build();

        return service.getMessages(filter, pageable);
    }

}
