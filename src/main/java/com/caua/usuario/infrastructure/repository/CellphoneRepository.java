package com.caua.usuario.infrastructure.repository;

import com.caua.usuario.infrastructure.entity.Cellphone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CellphoneRepository extends JpaRepository<Cellphone, Long> {
}
