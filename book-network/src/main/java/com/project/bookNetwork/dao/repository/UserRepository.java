package com.project.bookNetwork.dao.repository;

import com.project.bookNetwork.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author fares.belaid
 * 14/06/2024
 */
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
