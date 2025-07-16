package org.serratec.backend.repository;

import java.util.Optional;

import org.serratec.backend.entity.ResultadoJogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultadoJogoRepository extends JpaRepository<ResultadoJogo, Long> {
	
	Optional<ResultadoJogo>findByJogoId(Long id);
}