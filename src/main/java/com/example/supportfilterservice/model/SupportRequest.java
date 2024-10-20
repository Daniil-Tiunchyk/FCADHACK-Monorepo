package com.example.supportfilterservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupportRequest {
    @JsonProperty("Email")
    private String email;

    @JsonProperty("Endpoint")
    private String endpoint;

    @JsonProperty("Login")
    private String login;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("SupportLevel")
    private String supportLevel;

    @JsonProperty("Timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("UserID")
    private Long userId;

    @JsonProperty("Номер телефона")
    private String phoneNumber;

    @JsonProperty("Имя")
    private String firstName;

    @JsonProperty("Фамилия")
    private String lastName;

    @JsonProperty("Отчество")
    private String middleName;

    @JsonProperty("Пол")
    private String gender;

    @JsonProperty("Возраст")
    private Integer age;

    @JsonProperty("Дата рождения")
    private String birthDate;
}
