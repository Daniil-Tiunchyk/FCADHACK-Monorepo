package com.example.supportservice.service;

import com.example.supportservice.domain.entity.SupportMessage;
import com.example.supportservice.domain.repository.SupportMessageRepository;
import com.example.supportservice.web.dto.SupportMessageFilter;
import com.example.supportservice.util.SupportMessageSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SupportMessageServiceImpl implements SupportMessageService {

    private final SupportMessageRepository repository;

    public SupportMessageServiceImpl(SupportMessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveMessage(SupportMessage message) {
        repository.save(message);
    }

    @Override
    public SupportMessage getMessageById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("SupportMessage not found with id " + id));
    }

    @Override
    public Page<SupportMessage> getMessages(SupportMessageFilter filter, Pageable pageable) {
        return repository.findAll(SupportMessageSpecification.getSpecification(filter), pageable);
    }

}
