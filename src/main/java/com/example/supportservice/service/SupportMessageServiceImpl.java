package com.example.supportservice.service;

import com.example.supportservice.domain.entity.SupportMessage;
import com.example.supportservice.exception.ResourceNotFoundException;
import com.example.supportservice.domain.repository.SupportMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new ResourceNotFoundException("SupportMessage not found with id " + id));
    }

    @Override
    public List<SupportMessage> getMessages(int page, int size) {
        int start = page * size;
        return repository.findAll()
                .stream()
                .skip(start)
                .limit(size)
                .collect(Collectors.toList());
    }
}
