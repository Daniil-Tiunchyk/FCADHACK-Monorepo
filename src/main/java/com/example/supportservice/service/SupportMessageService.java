package com.example.supportservice.service;

import com.example.supportservice.domain.entity.SupportMessage;
import com.example.supportservice.web.dto.SupportMessageFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SupportMessageService {

    void saveMessage(SupportMessage message);

    SupportMessage getMessageById(Long id);

    Page<SupportMessage> getMessages(SupportMessageFilter filter, Pageable pageable);

}
