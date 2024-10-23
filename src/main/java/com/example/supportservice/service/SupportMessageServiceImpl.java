package com.example.supportservice.service;

import com.example.supportservice.domain.entity.SupportMessage;
import com.example.supportservice.domain.repository.SupportMessageRepository;
import com.example.supportservice.web.dto.SupportMessageFilter;
import com.example.supportservice.util.SupportMessageSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupportMessageServiceImpl {

    private final SupportMessageRepository repository;

    public SupportMessageServiceImpl(SupportMessageRepository repository) {
        this.repository = repository;
    }

    public void saveMessage(SupportMessage message) {
        repository.save(message);
    }

    public SupportMessage getMessageById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("SupportMessage not found with id " + id));
    }

    public List<SupportMessage> getMessages() {
        return repository.findAll();
    }
}
