package com.example.supportservice.web.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SupportMessageFilter {

    private LocalDateTime timestampFrom;
    private LocalDateTime timestampTo;
    private String userId;
    private String supportLevel;
    private String messageContent;
    private String email;
    private String endpoint;
    private String login;

}
