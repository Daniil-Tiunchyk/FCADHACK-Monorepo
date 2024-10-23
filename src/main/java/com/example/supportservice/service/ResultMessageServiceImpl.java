package com.example.supportservice.service;

import com.example.supportservice.domain.entity.ResultMessage;
import com.example.supportservice.domain.repository.ResultMessageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultMessageServiceImpl implements ResultMessageService {
    private final ResultMessageRepository repository;
    private final ObjectMapper objectMapper;

    public ResultMessageServiceImpl(ResultMessageRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void saveMessage(ResultMessage message) {
        repository.save(message);
    }

    @Override
    public ResultMessage getMessageById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ResultMessage not found with id " + id));
    }

    @Override
    public List<ResultMessage> getMessages(int page, int size) {
        int start = page * size;
        return repository.findAll()
                .stream()
                .skip(start)
                .limit(size)
                .collect(Collectors.toList());
    }

    //TODO Вынести в отдельный класс
    @KafkaListener(topics = "filtered-support-requests", groupId = "support-filter-group")
    public void listen(String messageJson) {
        try {
            // Преобразование JSON строки в JsonNode
            JsonNode requestArray = objectMapper.readTree(messageJson);

            // Извлечение списка ResultMessage
            List<ResultMessage> messages = parseToResultMessages(requestArray);

            // Сохранение сообщений
            for (ResultMessage message : messages) {
                saveMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<ResultMessage> parseToResultMessages(JsonNode requestArray) {
        List<ResultMessage> resultMessages = new ArrayList<>();

        for (JsonNode node : requestArray) {
            // Извлечение полей и создание нового ResultMessage
            ResultMessage message = ResultMessage.builder()
                    .email(node.get("Email").asText())  // Получение поля Email
                    .endpoint(node.get("Endpoint").asText())  // Получение поля Endpoint
                    .login(node.get("Login").asText())  // Получение поля Login
                    .message(node.get("Message").asText())  // Получение поля Message
                    .supportLevel(node.get("SupportLevel").asText())  // Получение поля SupportLevel
                    .timestamp((node.get("Timestamp").asLong()))  // Получение поля Timestamp как Instant
                    .userId(node.get("UserID").asLong())  // Получение поля UserID
                    .phoneNumber(node.get("Номер телефона").asText(null))  // Получение поля Номер телефона, значение по умолчанию null
                    .firstName(node.get("Имя").asText(null))  // Получение поля Имя
                    .lastName(node.get("Фамилия").asText(null))  // Получение поля Фамилия
                    .middleName(node.get("Отчество").asText(null))  // Получение поля Отчество
                    .gender(node.get("Пол").asText(null))  // Получение поля Пол
                    .age(node.has("Возраст") ? node.get("Возраст").asInt() : null)  // Получение поля Возраст
                    .birthDate(node.get("Дата рождения").asText(null))  // Получение поля Дата рождения
                    .build();

            resultMessages.add(message);
        }

        return resultMessages;
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
