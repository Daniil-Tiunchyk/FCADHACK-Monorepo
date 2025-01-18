package com.example.supportservice.domain.repository;

import com.example.supportservice.domain.entity.SupportMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportMessageRepository extends JpaRepository<SupportMessage, Long>, JpaSpecificationExecutor<SupportMessage> {
}
