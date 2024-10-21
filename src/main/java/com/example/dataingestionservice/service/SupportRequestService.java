package com.example.dataingestionservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SupportRequestService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public SupportRequestService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendSupportRequest(String request) {
        kafkaTemplate.send("support-requests", request);
    }
}