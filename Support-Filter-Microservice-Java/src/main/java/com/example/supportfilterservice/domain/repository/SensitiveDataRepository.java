package com.example.supportfilterservice.domain.repository;

import com.example.supportfilterservice.domain.entity.SensitiveData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensitiveDataRepository extends JpaRepository<SensitiveData, Long> {
}
