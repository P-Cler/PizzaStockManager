package org.serratec.backend.repository;

import org.serratec.backend.entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

    boolean existsByNome(String nome);
}
