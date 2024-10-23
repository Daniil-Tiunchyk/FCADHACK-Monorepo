package com.example.supportservice.web.controller;


import com.example.supportservice.domain.entity.ResultMessage;
import com.example.supportservice.service.ResultMessageService;
import com.example.supportservice.web.dto.SupportMessageFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/result-messages")
public class ResultMessageController {

    private final ResultMessageService service;

    public ResultMessageController(ResultMessageService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResultMessage getMessageById(@PathVariable Long id) {
        return service.getMessageById(id);
    }

    @GetMapping
    public List<ResultMessage> getMessages() {
        return service.getMessages();
    }
}