package org.serratec.backend.repository;

import java.util.List;

import org.serratec.backend.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    long countByJogoId(Long jogoId);
    
    List<Pedido> findByJogoId(Long jogoId);

}