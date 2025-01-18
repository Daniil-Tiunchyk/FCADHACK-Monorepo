package com.example.demo.service;

import com.example.demo.entity.SupportRequest;
import com.example.demo.repository.SupportRequestRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.Instant;

@Service
public class SupportRequestService {

    public SupportRequestService(SupportRequestRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    private final SupportRequestRepository repository;

    private final ObjectMapper objectMapper;

    public void loadData() {
        String filePath = "data.log";  // Путь к вашему файлу
        int processedCount = 0;        // Счётчик обработанных объектов
        long startTime = System.currentTimeMillis();  // Засекаем время начала

        // Очистка базы данных перед загрузкой новых данных
        repository.deleteAll();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Читаем файл построчно
            while ((line = br.readLine()) != null) {
                // Используем ObjectMapper для парсинга JSON-строки в объект SupportRequest
                SupportRequest request = objectMapper.readValue(line, SupportRequest.class);

                // Преобразуем поле Timestamp из long в Instant
                long timestampInSeconds = objectMapper.readTree(line).get("Timestamp").asLong();
                request.setTimestamp(Instant.ofEpochSecond(timestampInSeconds));

                // Сохраняем объект в базу данных
                repository.save(request);
                processedCount++;  // Увеличиваем счётчик обработанных объектов
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Засекаем время окончания и считаем время выполнения
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        // Выводим результаты
        System.out.println("Всего объектов перенесено в базу данных: " + processedCount);
        System.out.println("Время выполнения: " + totalTime + " миллисекунд");
    }
}
