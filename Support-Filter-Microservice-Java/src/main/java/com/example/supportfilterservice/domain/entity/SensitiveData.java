package com.example.supportfilterservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sensitive_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SensitiveData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("Email")
    @Column(columnDefinition = "TEXT")  // Убираем ограничение на длину
    private String email;

    @JsonProperty("Endpoint")
    @Column(columnDefinition = "TEXT")  // Убираем ограничение на длину
    private String endpoint;

    @JsonProperty("Login")
    @Column(columnDefinition = "TEXT")  // Убираем ограничение на длину
    private String login;

    @JsonProperty("Message")
    @Column(columnDefinition = "TEXT")  // Убираем ограничение на длину
    private String message;

    @JsonProperty("SupportLevel")
    @Column(name = "support_level", columnDefinition = "TEXT")  // Убираем ограничение на длину
    private String supportLevel;

    @JsonProperty("Timestamp")
    private Long timestamp;

    @JsonProperty("UserID")
    @Column(name = "user_id")
    private Long userId;

    @JsonProperty("Номер телефона")
    @Column(name = "phone_number", columnDefinition = "TEXT", nullable = true)  // Убираем ограничение на длину
    private String phoneNumber;

    @JsonProperty("Имя")
    @Column(name = "first_name", columnDefinition = "TEXT", nullable = true)  // Убираем ограничение на длину
    private String firstName;

    @JsonProperty("Фамилия")
    @Column(name = "last_name", columnDefinition = "TEXT", nullable = true)  // Убираем ограничение на длину
    private String lastName;

    @JsonProperty("Отчество")
    @Column(name = "middle_name", columnDefinition = "TEXT", nullable = true)  // Убираем ограничение на длину
    private String middleName;

    @JsonProperty("Пол")
    @Column(name = "gender", columnDefinition = "TEXT", nullable = true)  // Убираем ограничение на длину
    private String gender;

    @JsonProperty("Возраст")
    @Column(name = "age", nullable = true)
    private Integer age;

    @JsonProperty("Дата рождения")
    @Column(name = "birth_date", columnDefinition = "TEXT", nullable = true)  // Убираем ограничение на длину
    private String birthDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sensitive_data_id")
    private List<DetectedField> detectedFields;

    private LocalDateTime detectedAt;

}
