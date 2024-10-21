package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "support_requests")
public class SupportRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("Email")
    @Column(columnDefinition = "TEXT")
    private String email;

    @JsonProperty("Endpoint")
    @Column(columnDefinition = "TEXT")
    private String endpoint;

    @JsonProperty("Login")
    @Column(columnDefinition = "TEXT")
    private String login;

    @JsonProperty("Message")
    @Column(columnDefinition = "TEXT")
    private String message;

    @JsonProperty("SupportLevel")
    @Column(name = "support_level", columnDefinition = "TEXT")
    private String supportLevel;

    @JsonProperty("Timestamp")
    private Instant timestamp;

    @JsonProperty("UserID")
    @Column(name = "user_id")
    private Long userId;

    @JsonProperty("Номер телефона")
    @Column(name = "phone_number", columnDefinition = "TEXT")
    private String phoneNumber;

    @JsonProperty("Имя")
    @Column(name = "first_name", columnDefinition = "TEXT")
    private String firstName;

    @JsonProperty("Фамилия")
    @Column(name = "last_name", columnDefinition = "TEXT")
    private String lastName;

    @JsonProperty("Отчество")
    @Column(name = "middle_name", columnDefinition = "TEXT")
    private String middleName;

    @JsonProperty("Пол")
    @Column(name = "gender", columnDefinition = "TEXT")
    private String gender;

    @JsonProperty("Возраст")
    @Column(name = "age")
    private Integer age;

    @JsonProperty("Дата рождения")
    @Column(name = "birth_date", columnDefinition = "TEXT")
    private String birthDate;
}
