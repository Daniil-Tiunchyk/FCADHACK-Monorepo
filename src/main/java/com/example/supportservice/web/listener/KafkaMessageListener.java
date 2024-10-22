/*
package com.example.supportservice.web.listener;

import com.example.supportservice.domain.entity.SupportMessage;
import com.example.supportservice.service.SupportMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class KafkaMessageListener {

    private final SupportMessageService service;
    private final ObjectMapper objectMapper;

    public KafkaMessageListener(SupportMessageService service) {
        this.service = service;
        this.objectMapper = new ObjectMapper();
    }

    @KafkaListener(topics = "support-messages")
    public void listen(String messageJson) {
        try {
            // Преобразование JSON в объект
            SupportMessageDTO dto = objectMapper.readValue(messageJson, SupportMessageDTO.class);

            // Маппинг DTO на сущность
            SupportMessage message = SupportMessage.builder()
                    .email(dto.getEmail())
                    .endpoint(dto.getEndpoint())
                    .login(dto.getLogin())
                    .message(dto.getMessage())
                    .supportLevel(dto.getSupportLevel())
                    .timestamp(LocalDateTime.ofInstant(Instant.ofEpochSecond(dto.getTimestamp()), ZoneOffset.UTC))
                    .userId(dto.getUserID())
                    .build();

            service.saveMessage(message);
        } catch (Exception e) {
            // Обработка ошибок парсинга или сохранения
            e.printStackTrace();
        }
    }

    // DTO для приема данных из Kafka
    @Getter
    private static class SupportMessageDTO {
        private String Email;
        private String Endpoint;
        private String Login;
        private String Message;
        private String SupportLevel;
        private long Timestamp;
        private String UserID;
    }
}
*/
