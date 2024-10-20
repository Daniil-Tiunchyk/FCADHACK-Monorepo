package com.example.supportfilterservice.domain.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@Entity
@Embeddable
@Data
@RequiredArgsConstructor
public class DetectedField {

    private String field;
    private String regex;
    @Id
    @GeneratedValue
    private Long id;


    public DetectedField(String field, String pattern) {
        this.field = field;
        this.regex = pattern;
    }
}