package com.example.supportservice.domain.repository;

import com.example.supportservice.domain.entity.ResultMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultMessageRepository extends JpaRepository<ResultMessage, Long>, JpaSpecificationExecutor<ResultMessage> {
}
