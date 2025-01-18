package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonFieldCounter {

    public static void main(String[] args) {
        // Путь к файлу
        String filePath = "data.log";

        // Map для хранения названий полей и количества их вхождений
        Map<String, Integer> fieldCountMap = new HashMap<>();

        // ObjectMapper для парсинга JSON
        ObjectMapper objectMapper = new ObjectMapper();

        int objectCount = 0;  // Счётчик объектов

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Читаем файл построчно
            while ((line = br.readLine()) != null) {
                // Преобразуем строку в JsonNode для получения ключей
                JsonNode jsonNode = objectMapper.readTree(line);

                // Рекурсивно собираем ключи и увеличиваем счетчики
                countFieldNames(jsonNode, fieldCountMap);

                // Увеличиваем счётчик объектов
                objectCount++;
            }

            // Выводим количество вхождений для каждого поля
            System.out.println("Количество вхождений полей в файле:");
            for (Map.Entry<String, Integer> entry : fieldCountMap.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            // Выводим общее количество объектов
            System.out.println("\nВсего объектов в файле: " + objectCount);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Метод для рекурсивного обхода JSON и подсчета всех названий полей
    private static void countFieldNames(JsonNode jsonNode, Map<String, Integer> fieldCountMap) {
        if (jsonNode.isObject()) {
            Iterator<String> fieldIterator = jsonNode.fieldNames();
            while (fieldIterator.hasNext()) {
                String fieldName = fieldIterator.next();

                // Увеличиваем счетчик для каждого поля
                fieldCountMap.put(fieldName, fieldCountMap.getOrDefault(fieldName, 0) + 1);

                // Рекурсивно проверяем вложенные объекты
                countFieldNames(jsonNode.get(fieldName), fieldCountMap);
            }
        } else if (jsonNode.isArray()) {
            for (JsonNode arrayElement : jsonNode) {
                countFieldNames(arrayElement, fieldCountMap);
            }
        }
    }
}
