package com.example.dataingestionservice.repository;


import com.example.dataingestionservice.models.Person;
import com.example.dataingestionservice.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByPerson(Person person);
    @Modifying
    int deleteByPerson(Person person);
}
