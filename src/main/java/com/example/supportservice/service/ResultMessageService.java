package com.example.supportservice.service;

import com.example.supportservice.domain.entity.ResultMessage;
import com.example.supportservice.domain.repository.ResultMessageRepository;
import com.example.supportservice.util.SupportMessageSpecification;
import com.example.supportservice.web.dto.SupportMessageFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ResultMessageService {
    private final ResultMessageRepository repository;

    public ResultMessageService(ResultMessageRepository repository) {
        this.repository = repository;
    }

    public void saveMessage(ResultMessage message) {
        repository.save(message);
    }

    public ResultMessage getMessageById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Page<ResultMessage> getMessages(SupportMessageFilter filter, Pageable pageable) {

        return repository.findAll(SupportMessageSpecification.getSpecificationResult(filter), pageable);
    }

    @KafkaListener(topics = "filtered-support-requests", groupId = "support-filter-group")
    public void listen(String messageJson) {
        ResultMessage message = parseMessage(messageJson);
        saveMessage(message);
    }

    private ResultMessage parseMessage(String messageJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(messageJson, ResultMessage.class);
        } catch (JsonProcessingException e) {

            e.printStackTrace();
            return null;
        }
    }
}
