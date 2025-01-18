package com.example.supportservice.service;

import com.example.supportservice.domain.entity.ResultMessage;

import java.util.List;

public interface ResultMessageService {
    void saveMessage(ResultMessage message);

    ResultMessage getMessageById(Long id);

    List<ResultMessage> getMessages(int page, int size);
}
