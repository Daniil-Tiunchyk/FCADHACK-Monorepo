package com.example.supportservice.service;

import com.example.supportservice.domain.entity.ResultMessage;
import com.example.supportservice.domain.repository.ResultMessageRepository;
import com.example.supportservice.exception.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ResultMessageServiceImpl implements ResultMessageService {
    private final ResultMessageRepository repository;
    private final ObjectMapper objectMapper;
    private static final Logger logger = Logger.getLogger(ResultMessageServiceImpl.class.getName());

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
                .orElseThrow(() -> new ResourceNotFoundException("ResultMessage not found with id " + id));
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
            System.out.println("-------------------------------------------------------------------");
            System.out.println("-------------------------------------------------------------------");
            System.out.println("-------------------------------------------------------------------");
            System.out.println("-------------------------------------------------------------------");
            logger.info("До трансформации  " + messageJson);
            // Преобразование JSON строки в JsonNode
            JsonNode requestArray = objectMapper.readTree(messageJson);
            logger.info("После трансформации  " + requestArray);
            // Извлечение списка ResultMessage
            List<ResultMessage> messages = parseToResultMessages(requestArray);
            logger.info("После парсинга  " + messages);
            // Сохранение сообщений
            for (ResultMessage message : messages) {
                saveMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  List<ResultMessage> parseToResultMessages(JsonNode requestArray) {
        List<ResultMessage> resultMessages = new ArrayList<>();

        for (JsonNode node : requestArray) {
            // Извлечение полей и создание нового ResultMessage
            System.out.println(node);
            resultMessages.add(parseMessage(node));
        }

        return resultMessages;
    }
    private  ResultMessage parseMessage(JsonNode messageJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(messageJson, ResultMessage.class);

    }
}
