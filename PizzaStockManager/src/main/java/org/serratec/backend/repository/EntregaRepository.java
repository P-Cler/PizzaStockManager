package org.serratec.backend.repository;

import java.util.List;
import org.serratec.backend.entity.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {

    List<Entrega> findByEstoqueJogo_Jogo_Id(Long jogoId);
}