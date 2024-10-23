package com.example.supportfilterservice.flink.function;

import com.example.supportfilterservice.domain.DTO.Endpoint;
import com.example.supportfilterservice.domain.DTO.FilterMode;
import com.example.supportfilterservice.domain.DTO.RegexConfig;
import com.example.supportfilterservice.domain.entity.DetectedField;
import com.example.supportfilterservice.domain.entity.SensitiveData;
import com.example.supportfilterservice.domain.repository.SensitiveDataRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.api.common.functions.MapFunction;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestProcessor implements MapFunction<String, JsonNode> {
    private final List<Endpoint> disabledEndpoints;
    private final List<RegexConfig> regexConfigs;
    private final SensitiveDataRepository sensitiveDataRepository;
    private final ObjectMapper objectMapper;
    private static final Logger logger = Logger.getLogger(RequestProcessor.class.getName()); // Логгер

    public RequestProcessor(List<Endpoint> disabledEndpoints, List<RegexConfig> regexConfigs, SensitiveDataRepository sensitiveDataRepository, ObjectMapper objectMapper) {
        this.disabledEndpoints = disabledEndpoints;
        this.regexConfigs = regexConfigs;
        this.sensitiveDataRepository = sensitiveDataRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public JsonNode map(String value) throws Exception {
        logger.info("Пришло value: " + value); // Логирование входящего запроса

        JsonNode requestArray = objectMapper.readTree(value);
        List<JsonNode> processedRequests = new ArrayList<>();

        for (JsonNode request : requestArray) {
            if (shouldSkipRequest(request)) {
                logger.warning("Скип request: " + request); // Логирование пропуска запроса
                continue;
            }
            JsonNode copyRequest = request.deepCopy();
            // Обработка запроса и получение списка обнаруженных полей
            List<DetectedField> detectedFields = new ArrayList<>();
            boolean isRemoved = processRequest(request, detectedFields);

            if (!detectedFields.isEmpty()) {
                saveSensitiveData(copyRequest, detectedFields);
            }

            // Если объект был удалён, сохраняем его в БД
            if (!isRemoved) {
                processedRequests.add(request);
            }
        }

        // Возвращаем массив обработанных запросов в виде строки
        return objectMapper.valueToTree(processedRequests);
    }

    private boolean processRequest(JsonNode request, List<DetectedField> detectedFields) {
        boolean shouldRemove = false; // Флаг для удаления запроса

        if(regexConfigs!=null){
            for (RegexConfig regexConfig : regexConfigs) {
                if (!regexConfig.isEnabled()) {
                    continue;
                }
                if(shouldRemove){
                    break;
                }
                String fieldValue = getFieldValue(request, regexConfig.getField());
                if (fieldValue != null && Pattern.matches(regexConfig.getPattern(), fieldValue)) {
                    logger.info("Чувствительные данные для поля: " + regexConfig.getField()); // Логирование обнаруженных данных
                    // Обработка чувствительных данных
                    shouldRemove = handleSensitiveData(request, regexConfig, detectedFields) || shouldRemove;
                }
            }
        }

        return shouldRemove; // Возвращаем, нужно ли удалять объект
    }

    private boolean handleSensitiveData(JsonNode request, RegexConfig regexConfig, List<DetectedField> detectedFields) {
        boolean shouldRemove = false;
        if (regexConfig.isModeActive(FilterMode.REMOVE_OBJECT)) {
            shouldRemove = true; // Установить флаг удаления
            logger.info("Удаление объекта."); // Логирование удаления объекта
        } else if (regexConfig.isModeActive(FilterMode.REMOVE_FIELD)) {
            logger.info("Поле удалено removed: " + regexConfig.getField()); // Логирование удаления поля
            removeField(request, regexConfig.getField());
        } else if (regexConfig.isModeActive(FilterMode.HIDE_DATA)) {
            hideData(request, regexConfig.getField(), regexConfig.getPattern());
        }

        // Получаем режим с наименьшим приоритетом
        Optional<FilterMode> lowestModeOpt = regexConfig.getLowestPriorityMode();
        // Добавляем обнаруженные поля в список с указанием режима
        lowestModeOpt.ifPresent(filterMode -> detectedFields.add(new DetectedField(regexConfig.getField(), regexConfig.getPattern(), filterMode)));
        return shouldRemove; // Возвращаем, нужно ли удалять объект
    }
    private boolean shouldSkipRequest(JsonNode request) {
        String endpointText = request.get("Endpoint").asText();
        Endpoint endpoint = new Endpoint(endpointText, true);
        if (disabledEndpoints != null) {
            boolean shouldSkip = disabledEndpoints.contains(endpoint);
            if (shouldSkip) {
                logger.info("Request Скипаем: " + endpointText); // Логирование пропуска по причине отключенного эндпоинта
            }
            return shouldSkip;
        }
        return false;
    }

    private void saveSensitiveData(JsonNode request, List<DetectedField> detectedFields) {
        try {
            // Преобразуем JsonNode в объект SensitiveData
            SensitiveData sensitiveData = objectMapper.treeToValue(request, SensitiveData.class);

            // Дополняем объект дополнительными полями
            sensitiveData.setDetectedFields(detectedFields);
            sensitiveData.setDetectedAt(LocalDateTime.now());
            logger.info("ЧД данные сохранены: " + sensitiveData); // Логирование сохранения данных

            // Сохраняем в БД
            sensitiveDataRepository.save(sensitiveData);
        } catch (IOException e) {
            logger.severe("Ошибка сохранения ЧД: " + e.getMessage()); // Логирование ошибок сохранения
            e.printStackTrace();
        }
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