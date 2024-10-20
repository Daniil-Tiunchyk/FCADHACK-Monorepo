package com.example.supportfilterservice.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessedSupportRequest {
    //TODO Добавить поля

    private String email;
    private String endpoint;
    private String login;
    private String message;
    private String supportLevel;
    private LocalDateTime timestamp;
    private String userId;

}
