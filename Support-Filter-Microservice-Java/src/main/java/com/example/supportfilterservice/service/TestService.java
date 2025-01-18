package com.example.supportfilterservice.service;

import com.example.supportfilterservice.domain.repository.SensitiveDataRepository;
import com.example.supportfilterservice.flink.function.RequestProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    private final RegexConfigService regexConfigService;

    private final EndpointService endpointService;
    private final SensitiveDataRepository sensitiveDataRepository;
    private final ObjectMapper objectMapper;
    public TestService(RegexConfigService regexConfigService, EndpointService endpointService, SensitiveDataRepository sensitiveDataRepository, ObjectMapper objectMapper) {
        this.regexConfigService = regexConfigService;
        this.endpointService = endpointService;
        this.sensitiveDataRepository = sensitiveDataRepository;
        this.objectMapper = objectMapper;
    }
    public String test(String value){
        RequestProcessor requestProcessor = new RequestProcessor(endpointService.getAllEndpoints(),
                regexConfigService.getAllRegexConfigs(),sensitiveDataRepository,objectMapper);
        String result = "quu";
        try {
            result = objectMapper.writeValueAsString(requestProcessor.map(value));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
