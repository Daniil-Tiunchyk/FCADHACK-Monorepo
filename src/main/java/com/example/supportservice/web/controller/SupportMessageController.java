package com.example.supportservice.web.controller;

import com.example.supportservice.domain.entity.SupportMessage;
import com.example.supportservice.service.SupportMessageService;
import com.example.supportservice.service.SupportMessageServiceImpl;
import com.example.supportservice.web.dto.SupportMessageFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/support-messages")
public class SupportMessageController {

    private final SupportMessageServiceImpl service;

    public SupportMessageController(SupportMessageServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public SupportMessage getMessageById(@PathVariable Long id) {
        return service.getMessageById(id);
    }

    @GetMapping
    public List<SupportMessage> getMessages() {
        return service.getMessages();
    }
}
