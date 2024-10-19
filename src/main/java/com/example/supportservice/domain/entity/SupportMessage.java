package com.example.supportservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "support_messages")
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

    @Column(columnDefinition = "TEXT")
    private String message;

    private String supportLevel;

    private LocalDateTime timestamp;

    private String userId;

}
