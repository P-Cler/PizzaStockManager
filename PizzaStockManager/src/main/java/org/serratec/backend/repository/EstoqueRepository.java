package org.serratec.backend.repository;

import java.util.List;
import java.util.Optional;

import org.serratec.backend.entity.EstoqueJogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoqueRepository extends JpaRepository<EstoqueJogo, Long> {

	List<EstoqueJogo> findByJogoId(Long jogoId);
	
    boolean existsByJogoIdAndIngredienteId(Long jogoId, Long ingredienteId);

    Optional<EstoqueJogo> findByJogoIdAndIngredienteId(Long jogoId, Long ingredienteId);

}
