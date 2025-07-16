package org.serratec.backend.repository;

import java.util.List;
import org.serratec.backend.entity.JogoReceita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JogoReceitaRepository extends JpaRepository<JogoReceita, Long> {
    boolean existsByJogoIdAndReceitaId(Long jogoId, Long receitaId);

    List<JogoReceita> findByJogoId(Long jogoId);
}