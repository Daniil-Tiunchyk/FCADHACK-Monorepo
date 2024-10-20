package com.example.supportfilterservice.flink.function;

import com.example.supportfilterservice.domain.DTO.FilterMode;
import com.example.supportfilterservice.domain.DTO.RegexConfig;
import com.example.supportfilterservice.domain.entity.DetectedField;
import com.example.supportfilterservice.domain.entity.SensitiveData;
import com.example.supportfilterservice.domain.repository.SensitiveDataRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.api.common.functions.MapFunction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestProcessor implements MapFunction<String, JsonNode> {
    private final List<String> disabledEndpoints;
    private final List<RegexConfig> regexConfigs;
    private final SensitiveDataRepository sensitiveDataRepository;
    private final ObjectMapper objectMapper;

    public RequestProcessor(List<String> disabledEndpoints, List<RegexConfig> regexConfigs, SensitiveDataRepository sensitiveDataRepository, ObjectMapper objectMapper) {
        this.disabledEndpoints = disabledEndpoints;
        this.regexConfigs = regexConfigs;
        this.sensitiveDataRepository = sensitiveDataRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public JsonNode map(String value) throws Exception {
        // Преобразуем строку в массив JSON
        JsonNode requestArray = objectMapper.readTree(value);
        List<JsonNode> processedRequests = new ArrayList<>();

        for (JsonNode request : requestArray) {
            String endpoint = request.get("Endpoint").asText();

            if (disabledEndpoints.contains(endpoint)) {
                disabledEndpoints.remove(endpoint);
            } else {
                List<DetectedField> detectedFields = new ArrayList<>(); // Список для хранения обнаруженных полей
                boolean isSensitiveDataFound = false; // Флаг для проверки наличия чувствительных данных

                for (RegexConfig regexConfig : regexConfigs) {
                    String fieldValue = getFieldValue(request, regexConfig.getField());

                    if (fieldValue != null && Pattern.matches(regexConfig.getPattern(), fieldValue)) {
                        isSensitiveDataFound = true; // Обнаружены чувствительные данные

                        if (regexConfig.isModeActive(FilterMode.HIDE_DATA)) {
                            hideData(request, regexConfig.getField(), regexConfig.getPattern());
                        }
                        if (regexConfig.isModeActive(FilterMode.REMOVE_FIELD)) {
                            removeField(request, regexConfig.getField());
                        }
                        if (regexConfig.isModeActive(FilterMode.REMOVE_OBJECT)) {
                            continue; // Переход к следующему объекту, если он должен быть удален
                        }
                        // Добавляем обнаруженные поля в список
                        detectedFields.add(new DetectedField(regexConfig.getField(), regexConfig.getPattern()));
                    }
                }

                // Если обнаружены чувствительные данные, создаем объект SensitiveData и сохраняем его
                if (isSensitiveDataFound) {
                    SensitiveData sensitiveData = SensitiveData.builder()
                            .originalData(request.toString()) // Сохраняем оригинальные данные
                            .detectedFields(detectedFields) // Сохраняем список обнаруженных полей
                            .detectedAt(LocalDateTime.now()) // Текущая дата и время
                            .build();

                    sensitiveDataRepository.save(sensitiveData); // Сохраняем в БД
                }

                processedRequests.add(request); // Добавляем обработанный объект в список
            }
        }

        // Возвращаем массив обработанных запросов в виде строки
        return objectMapper.valueToTree(processedRequests);
    }

    private String getFieldValue(JsonNode request, String fieldName) {
        JsonNode field = request.get(fieldName);
        return field != null ? field.asText() : null;
    }

    private void hideData(JsonNode request, String fieldName, String pattern) {
        String currentValue = request.get(fieldName).asText();
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(currentValue);
        StringBuffer modifiedValue = new StringBuffer();
        while (matcher.find()) {
            String match = matcher.group();
            matcher.appendReplacement(modifiedValue, "*".repeat(match.length()));
        }
        matcher.appendTail(modifiedValue);

        ((ObjectNode) request).put(fieldName, modifiedValue.toString());
    }

    private void removeField(JsonNode request, String fieldName) {
        ((ObjectNode) request).remove(fieldName);
    }
}