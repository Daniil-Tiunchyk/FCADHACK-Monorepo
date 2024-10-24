package com.example.supportfilterservice.domain.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Endpoint implements Serializable {
    @NotNull(message = "Endpoint must not be null.")
    private String endpoint;
    private boolean isEnabled;
}
