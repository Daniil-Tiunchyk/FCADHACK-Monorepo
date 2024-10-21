package com.example.supportfilterservice.service;

import com.example.supportfilterservice.domain.DTO.Endpoint;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.redis.listener.ChannelTopic;
import java.util.ArrayList;

@Service
public class EndpointService {

    private final RedisTemplate<String, List<Endpoint>> redisTemplate;
    @Qualifier("disabledEndpointsUpdatesTopic")
    private final ChannelTopic disabledEndpointsUpdatesTopic;

    public EndpointService(RedisTemplate<String, List<Endpoint>> redisTemplate, ChannelTopic disabledEndpointsUpdatesTopic) {
        this.redisTemplate = redisTemplate;
        this.disabledEndpointsUpdatesTopic = disabledEndpointsUpdatesTopic;
    }

    public List<Endpoint> getAllEndpoints() {
        List<Endpoint> endpoints = redisTemplate.opsForValue().get("endpoints");
        return endpoints != null ? endpoints : new ArrayList<>();
    }

    public void createEndpoint(Endpoint endpoint) {
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
        redisTemplate.convertAndSend(disabledEndpointsUpdatesTopic.getTopic(), endpoints);
    }
}
