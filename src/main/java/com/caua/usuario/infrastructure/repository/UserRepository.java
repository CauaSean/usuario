package com.caua.usuario.infrastructure.repository;

import com.caua.usuario.infrastructure.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {

    void deleteByEmail(String email);
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
}
