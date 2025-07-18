package org.serratec.backend.repository;

import org.serratec.backend.entity.LoteEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoteEstoqueRepository extends JpaRepository<LoteEstoque, Long> {}