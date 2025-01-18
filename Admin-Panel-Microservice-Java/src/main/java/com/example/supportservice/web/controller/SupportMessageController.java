package com.example.supportservice.web.controller;

import com.example.supportservice.domain.entity.SupportMessage;
import com.example.supportservice.service.SupportMessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<SupportMessage> getMessages(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        return service.getMessages(page, size);
    }

    @PostMapping
    public void saveMessage(@RequestBody SupportMessage message) {
        service.saveMessage(message);
    }
}
