package com.example.supportfilterservice.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//TODO Сделать просто запрос на изменения включения
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Endpoint {
    private String endpoint;
    private boolean isEnabled;
}
