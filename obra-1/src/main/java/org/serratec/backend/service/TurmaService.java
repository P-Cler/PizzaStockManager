package org.serratec.backend.service;

import java.util.List;

import org.serratec.backend.DTO.TurmaRequest;
import org.serratec.backend.DTO.TurmaResponse;
import org.serratec.backend.entity.Turma;
import org.serratec.backend.exceptions.ResourceNotFoundException;
import org.serratec.backend.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TurmaService {

	@Autowired
    private TurmaRepository turmaRepository;

    public TurmaResponse adicionar(TurmaRequest request) {
        Turma turma = new Turma();
        turma.setNome(request.nome());
        turmaRepository.save(turma);
        return new TurmaResponse(turma.getId(), turma.getNome());
    }

    public TurmaResponse atualizar(Long id, TurmaRequest request) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turma não encontrada."));

        turma.setNome(request.nome());
        turmaRepository.save(turma);
        return new TurmaResponse(turma.getId(), turma.getNome());
    }

    public void deletar(Long id) {
        if (!turmaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Turma não encontrada.");
        }
        turmaRepository.deleteById(id);
    }

    public List<TurmaResponse> listar() {
        return turmaRepository.findAll().stream()
                .map(t -> new TurmaResponse(t.getId(), t.getNome()))
                .toList();
    }
}
