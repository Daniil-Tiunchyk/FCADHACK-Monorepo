package com.example.supportfilterservice.domain.DTO;

import lombok.*;

import java.util.EnumSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public  class RegexConfig {
    private boolean isEnabled;
    private String field;
    private Set<FilterMode> modes = EnumSet.noneOf(FilterMode.class); // Моды фильтрации
    private String pattern;
    public boolean isModeActive(FilterMode mode) {
        return this.modes.contains(mode);
    }
}
