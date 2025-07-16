package org.serratec.backend.repository;

import java.util.List;
import java.util.Optional;
import org.serratec.backend.entity.JogoAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JogoAlunoRepository extends JpaRepository<JogoAluno, Long> {

    boolean existsByJogoIdAndAlunoId(Long jogoId, Long alunoId);

    Optional<JogoAluno> findByJogoIdAndAlunoId(Long jogoId, Long alunoId);

    List<JogoAluno> findByJogoId(Long jogoId);

    List<JogoAluno> findByAlunoId(Long alunoId);
}