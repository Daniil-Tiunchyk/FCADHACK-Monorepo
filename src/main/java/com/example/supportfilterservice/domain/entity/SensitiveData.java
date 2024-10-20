package com.example.supportfilterservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sensitive_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensitiveData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalData;

    @ElementCollection
    @CollectionTable(name = "detected_fields", joinColumns = @JoinColumn(name = "sensitive_data_id"))
    private List<DetectedField> detectedFields;

    private LocalDateTime detectedAt;

}
