package org.serratec.backend.repository;

import java.util.Optional;
import org.serratec.backend.entity.Professor;
import org.serratec.backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    Optional<Professor> findByUsuario(Usuario usuario);
}