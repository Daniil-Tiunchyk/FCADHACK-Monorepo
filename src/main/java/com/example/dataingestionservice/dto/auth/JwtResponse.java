package com.example.dataingestionservice.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponse {
    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;
    private List<String> roles;
}
