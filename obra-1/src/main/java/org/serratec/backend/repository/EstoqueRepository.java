package org.serratec.backend.repository;

import org.serratec.backend.entity.EstoqueJogo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueRepository extends JpaRepository<EstoqueJogo, Long> {

}
