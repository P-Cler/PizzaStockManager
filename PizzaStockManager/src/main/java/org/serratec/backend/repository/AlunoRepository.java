package org.serratec.backend.repository;

import java.util.Optional;

import org.serratec.backend.entity.Aluno;
import org.serratec.backend.entity.Professor;
import org.serratec.backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    boolean existsByCodMatricula(String codMatricula);

    Optional<Aluno> findByUsuario(Usuario usuario);

}
