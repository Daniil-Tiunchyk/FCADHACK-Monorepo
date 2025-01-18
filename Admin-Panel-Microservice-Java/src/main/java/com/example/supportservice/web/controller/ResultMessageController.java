package com.example.supportservice.web.controller;

import com.example.supportservice.domain.entity.ResultMessage;
import com.example.supportservice.service.ResultMessageService;
import org.springframework.web.bind.annotation.*;

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
    public List<ResultMessage> getMessages(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        return service.getMessages(page, size);
    }

    @PostMapping
    public void saveMessage(@RequestBody ResultMessage message) {
        service.saveMessage(message);
    }
}
