package com.project.bookNetwork.dao.repository;

import com.project.bookNetwork.dao.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author fares.belaid
 * 14/06/2024
 */
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);
}
