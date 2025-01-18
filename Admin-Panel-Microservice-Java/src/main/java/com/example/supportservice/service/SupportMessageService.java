package com.example.supportservice.service;

import com.example.supportservice.domain.entity.SupportMessage;

import java.util.List;

public interface SupportMessageService {
    void saveMessage(SupportMessage message);

    SupportMessage getMessageById(Long id);

    List<SupportMessage> getMessages(int page, int size);
}
