package com.example.supportfilterservice.flink;

import com.example.supportfilterservice.config.AppConfig;
import com.example.supportfilterservice.domain.repository.SensitiveDataRepository;
import com.example.supportfilterservice.flink.function.CustomFilterFunction;
import com.example.supportfilterservice.model.SupportRequest;
import com.example.supportfilterservice.util.ConfigUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Component
public class SupportRequestFilterJob {

    private final StreamExecutionEnvironment env;
    private final AppConfig appConfig;
    private final SensitiveDataRepository sensitiveDataRepository;

    public SupportRequestFilterJob(StreamExecutionEnvironment env, AppConfig appConfig, SensitiveDataRepository sensitiveDataRepository) {
        this.env = env;
        this.appConfig = appConfig;
        this.sensitiveDataRepository = sensitiveDataRepository;
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

        // Извлекаем нужные параметры из appConfig
        boolean regexEnabled = appConfig.getRegex().isEnabled();
        String regexPattern = appConfig.getRegex().getPattern();
        boolean excludeModeEnabled = Boolean.parseBoolean(ConfigUtil.getProperty("mode.exclude.enabled", "false"));
        boolean replaceModeEnabled = Boolean.parseBoolean(ConfigUtil.getProperty("mode.replace.enabled", "false"));
        boolean removeModeEnabled = Boolean.parseBoolean(ConfigUtil.getProperty("mode.remove.enabled", "false"));
//        List<String> disabledEndpoints = appConfig.getEndpoint().getDisabledEndpoints();
        List<String> disabledEndpoints = Arrays.asList(
                "https://example.com/api/endpoint1",
                "https://example.com/api/endpoint2",
                "https://example.com/api/endpoint3",
                "https://example.com/api/endpoint4",
                "https://example.com/api/endpoint5"
        );

        // Передаем только примитивные данные в CustomFilterFunction
        env
                .addSource(consumer)
                .map(value -> objectMapper.readValue(value, SupportRequest.class))
                .filter(new CustomFilterFunction(
                        regexEnabled,
                        regexPattern,
                        excludeModeEnabled,
                        replaceModeEnabled,
                        removeModeEnabled,
                        disabledEndpoints))
                .map(objectMapper::writeValueAsString)
                .addSink(producer);

        // TODO Сохранять отфильтрованное в БД
        env.execute("Support Request Filter Job");
    }

}
