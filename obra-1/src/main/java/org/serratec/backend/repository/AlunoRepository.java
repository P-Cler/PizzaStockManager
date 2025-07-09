package org.serratec.backend.repository;

import java.util.Optional;

import org.serratec.backend.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    boolean existsByEmail(String email);

    boolean existsByCodMatricula(String codMatricula);

    Optional<Aluno> findByEmail(String email);
}
