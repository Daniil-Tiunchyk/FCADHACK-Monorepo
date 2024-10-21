package com.example.supportfilterservice.domain.DTO;

public enum FilterMode {
    HIDE_DATA(3),      // Скрывать данные
    REMOVE_FIELD(2),   // Удалять поля
    REMOVE_OBJECT(1);  // Удалять объект

    private final int priority;

    FilterMode(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
