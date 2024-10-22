package com.example.supportfilterservice.flink;

import com.example.supportfilterservice.domain.DTO.Endpoint;
import com.example.supportfilterservice.domain.DTO.RegexConfig;
import com.example.supportfilterservice.domain.repository.SensitiveDataRepository;
import com.example.supportfilterservice.flink.function.RequestProcessor;
import com.example.supportfilterservice.service.EndpointService;
import com.example.supportfilterservice.service.RegexConfigService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.*;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class SupportRequestFilterJob {
    private final StreamExecutionEnvironment env;
    private final RegexConfigService regexConfigService;
    private final EndpointService endpointService;
    private final RedisConnectionFactory jedisConnectionFactory;
    private final RedisMessageListenerContainer container; // Используем бин
    private final SensitiveDataRepository sensitiveDataRepository;
    private final AtomicReference<List<RegexConfig>> regexConfigs = new AtomicReference<>(new ArrayList<>());
    private final AtomicReference<List<Endpoint>> disabledEndpoints = new AtomicReference<>(new ArrayList<>());

    public SupportRequestFilterJob(StreamExecutionEnvironment env, RegexConfigService regexConfigService,
                                   EndpointService endpointService, RedisConnectionFactory jedisConnectionFactory,
                                   RedisMessageListenerContainer container, SensitiveDataRepository sensitiveDataRepository
                                   )  {
        this.env = env;
        this.regexConfigService = regexConfigService;
        this.endpointService = endpointService;
        this.jedisConnectionFactory = jedisConnectionFactory;
        this.container = container;
        this.sensitiveDataRepository = sensitiveDataRepository;
        loadInitialConfigs();
        startRedisListener();
    }


    public void execute() throws Exception {
        Properties kafkaProps = new Properties();
        kafkaProps.setProperty("bootstrap.servers", "localhost:9092");
        kafkaProps.setProperty("group.id", "support-filter-group");

        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>(
                "support-requests",
                new SimpleStringSchema(),
                kafkaProps);

        FlinkKafkaProducer<String> producer = new FlinkKafkaProducer<>(
                "filtered-support-requests",
                new SimpleStringSchema(),
                kafkaProps);
        ObjectMapper objectMapper = new ObjectMapper();
        env.addSource(consumer)
                .map(new RequestProcessor(disabledEndpoints.get(), regexConfigs.get(), sensitiveDataRepository, objectMapper))
                .filter(Objects::nonNull)
                .map(objectMapper::writeValueAsString)
                .addSink(producer);
        env.execute("Support Request Filter Job");
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
