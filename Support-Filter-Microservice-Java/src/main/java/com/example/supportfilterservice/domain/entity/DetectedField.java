package com.example.supportfilterservice.domain.entity;

import com.example.supportfilterservice.domain.DTO.FilterMode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Entity
@Data
@AllArgsConstructor
@ToString
public class DetectedField {
    private String field;
    private String regex;
    private FilterMode mode; // Новое поле для режима

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public DetectedField(String field, String pattern, FilterMode mode) {
        this.field = field;
        this.regex = pattern;
        this.mode = mode; // Инициализируем режим
    }
}
