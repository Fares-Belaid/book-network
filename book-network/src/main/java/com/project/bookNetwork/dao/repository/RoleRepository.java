package com.project.bookNetwork.dao.repository;

import com.project.bookNetwork.dao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author fares.belaid
 * 14/06/2024
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
