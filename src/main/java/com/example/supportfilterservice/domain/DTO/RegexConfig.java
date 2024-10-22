package com.example.supportfilterservice.domain.DTO;

import lombok.*;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public  class RegexConfig implements Serializable {
    private boolean isEnabled;
    private String field;
    private Set<FilterMode> modes = EnumSet.noneOf(FilterMode.class); // Моды фильтрации
    private String pattern;
    public boolean isModeActive(FilterMode mode) {
        return this.modes.contains(mode);
    }
    public Optional<FilterMode> getLowestPriorityMode() {
        return modes.stream()
                .min((mode1, mode2) -> Integer.compare(mode1.getPriority(), mode2.getPriority()));
    }
}
