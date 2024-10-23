package com.example.supportfilterservice.flink;

import com.example.supportfilterservice.domain.DTO.Endpoint;
import com.example.supportfilterservice.domain.DTO.RegexConfig;
import com.example.supportfilterservice.domain.repository.SensitiveDataRepository;
import com.example.supportfilterservice.flink.function.RequestProcessor;
import com.example.supportfilterservice.service.EndpointService;
import com.example.supportfilterservice.service.RegexConfigService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class SupportRequestFilterJob {
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;
    private final RegexConfigService regexConfigService;
    private final EndpointService endpointService;
    private final RedisMessageListenerContainer container; // Используем бин
    private final SensitiveDataRepository sensitiveDataRepository;
    private final AtomicReference<List<RegexConfig>> regexConfigs = new AtomicReference<>(new ArrayList<>());
    private final AtomicReference<List<Endpoint>> disabledEndpoints = new AtomicReference<>(new ArrayList<>());

    public SupportRequestFilterJob(RegexConfigService regexConfigService,
                                   EndpointService endpointService,
                                   KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper, RedisMessageListenerContainer container, SensitiveDataRepository sensitiveDataRepository
                                   )  {
        this.kafkaTemplate = kafkaTemplate;

        this.regexConfigService = regexConfigService;
        this.endpointService = endpointService;
        this.objectMapper = objectMapper;

        this.container = container;
        this.sensitiveDataRepository = sensitiveDataRepository;
        loadInitialConfigs();
        startRedisListener();
    }



    @KafkaListener(topics = "support-requests", groupId = "support-filter-group")
    public void processRequest(String value) {
        RequestProcessor requestProcessor = new RequestProcessor(disabledEndpoints.get(), regexConfigs.get(), sensitiveDataRepository, objectMapper);

        try {
            JsonNode resultNode = requestProcessor.map(value);
            String resultString = objectMapper.writeValueAsString(resultNode);

            kafkaTemplate.send("filtered-support-requests", resultString);
            // Отправка результата в Kafka

        } catch (Exception e) {
            e.printStackTrace();
            // Логирование ошибки или обработка исключения
        }
    }
    private void loadInitialConfigs() {
        regexConfigs.set(regexConfigService.getSortedRegexConfigs());
        disabledEndpoints.set(endpointService.getAllEndpoints());
    }
    private void startRedisListener() {
        new Thread(() -> {
            // Добавляем слушатели для обновления конфигураций
            container.addMessageListener((message, pattern) -> {
                regexConfigs.set(regexConfigService.getSortedRegexConfigs());
            }, new ChannelTopic("regexConfigUpdates"));

            container.addMessageListener((message, pattern) -> {
                disabledEndpoints.set(endpointService.getAllEndpoints());
            }, new ChannelTopic("disabledEndpointsUpdates"));

            // Запускаем контейнер, если он еще не запущен
            if (!container.isRunning()) {
                container.start();
            }
        }).start();
    }
}
