package com.example.supportfilterservice.service;

import com.example.supportfilterservice.domain.DTO.Endpoint;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.listener.ChannelTopic;

import java.util.ArrayList;
import java.util.List;

@Service
public class EndpointService {

    private final RedisTemplate<String, List<Endpoint>> redisTemplate;

    @Qualifier("disabledEndpointsUpdatesTopic")
    private final ChannelTopic disabledEndpointsUpdatesTopic;

    public EndpointService(@Qualifier("endpointRedisTemplate") RedisTemplate<String, List<Endpoint>> redisTemplate,
                           ChannelTopic disabledEndpointsUpdatesTopic) {
        this.redisTemplate = redisTemplate;
        this.disabledEndpointsUpdatesTopic = disabledEndpointsUpdatesTopic;
    }

    public List<Endpoint> getAllEndpoints() {
        List<Endpoint> endpoints = redisTemplate.opsForValue().get("endpoints");
        return endpoints != null ? endpoints : new ArrayList<>();
    }

    public void createEndpoint(Endpoint endpoint) {
        if (endpoint == null) {
            throw new IllegalArgumentException("Endpoint must not be null.");
        }

        List<Endpoint> endpoints = getAllEndpoints();

        if (endpoints.stream().anyMatch(e -> e.getEndpoint().equals(endpoint.getEndpoint()))) {
            throw new IllegalArgumentException("Endpoint already exists.");
        }

        endpoints.add(endpoint);
        redisTemplate.opsForValue().set("endpoints", endpoints);

        // Публикуем обновление
        publishDisabledEndpointsUpdate(endpoints);
    }

    public void updateEndpoint(String endpointName, boolean isEnabled) {
        if (endpointName == null) {
            throw new IllegalArgumentException("Endpoint name must not be null.");
        }

        List<Endpoint> endpoints = getAllEndpoints();
        Endpoint existingEndpoint = endpoints.stream()
                .filter(e -> e.getEndpoint().equals(endpointName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Endpoint not found."));

        existingEndpoint.setEnabled(isEnabled);
        redisTemplate.opsForValue().set("endpoints", endpoints);

        // Публикуем обновление
        publishDisabledEndpointsUpdate(endpoints);
    }

    public void deleteEndpoint(String endpointName) {
        if (endpointName == null) {
            throw new IllegalArgumentException("Endpoint name must not be null.");
        }

        List<Endpoint> endpoints = getAllEndpoints();
        boolean removed = endpoints.removeIf(e -> e.getEndpoint().equals(endpointName));

        if (!removed) {
            throw new IllegalArgumentException("Endpoint not found.");
        }

        redisTemplate.opsForValue().set("endpoints", endpoints);

        // Публикуем обновление
        publishDisabledEndpointsUpdate(endpoints);
    }

    private void publishDisabledEndpointsUpdate(List<Endpoint> endpoints) {
        if (endpoints == null) {
            throw new IllegalArgumentException("Endpoints list must not be null.");
        }
        redisTemplate.convertAndSend(disabledEndpointsUpdatesTopic.getTopic(), endpoints);
    }
}