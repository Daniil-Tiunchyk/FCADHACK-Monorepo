package com.example.supportservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sensitive_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupportMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String endpoint;

    private String login;

    @Column(columnDefinition = "TEXT")  // Убираем ограничение на длину
    private String message;

    private String supportLevel;

    private Long timestamp; // Используем Instant для соответствия

    @Column(name = "user_id")
    private String userId; // Убедитесь, что тип соответствует вашим данным

    @Column(name = "phone_number", columnDefinition = "TEXT")  // Убираем ограничение на длину
    private String phoneNumber;

    @Column(name = "first_name", columnDefinition = "TEXT")  // Убираем ограничение на длину
    private String firstName;

    @Column(name = "last_name", columnDefinition = "TEXT")  // Убираем ограничение на длину
    private String lastName;

    @Column(name = "middle_name", columnDefinition = "TEXT")  // Убираем ограничение на длину
    private String middleName;

    @Column(name = "gender", columnDefinition = "TEXT")  // Убираем ограничение на длину
    private String gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "birth_date", columnDefinition = "TEXT")  // Убираем ограничение на длину
    private String birthDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sensitive_data_id")
    private List<DetectedField> detectedFields;

    private LocalDateTime detectedAt;
}
