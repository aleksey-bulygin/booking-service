package com.project.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.booking.entity.Token;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findOneByValue(String name);
}
