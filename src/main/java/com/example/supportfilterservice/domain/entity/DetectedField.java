package com.example.supportfilterservice.domain.entity;

import com.example.supportfilterservice.domain.DTO.FilterMode;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Embeddable
@Data
@AllArgsConstructor
public class DetectedField {
    private String field;
    private String regex;
    private FilterMode mode; // Новое поле для режима

    @Id
    @GeneratedValue
    private Long id;

    public DetectedField(String field, String pattern, FilterMode mode) {
        this.field = field;
        this.regex = pattern;
        this.mode = mode; // Инициализируем режим
    }
}
