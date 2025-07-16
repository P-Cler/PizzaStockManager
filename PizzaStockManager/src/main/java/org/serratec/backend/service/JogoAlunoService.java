package org.serratec.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.backend.DTO.AlunoResponseDTO;
import org.serratec.backend.DTO.JogoAlunoRequestDTO;
import org.serratec.backend.entity.Aluno;
import org.serratec.backend.entity.Jogo;
import org.serratec.backend.entity.JogoAluno;
import org.serratec.backend.exceptions.ResourceNotFoundException;
import org.serratec.backend.repository.AlunoRepository;
import org.serratec.backend.repository.JogoAlunoRepository;
import org.serratec.backend.repository.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JogoAlunoService {

    @Autowired private JogoAlunoRepository jogoAlunoRepository;
    @Autowired private JogoRepository jogoRepository;
    @Autowired private AlunoRepository alunoRepository;

    public String associar(JogoAlunoRequestDTO requestDTO) {
        if (jogoAlunoRepository.existsByJogoIdAndAlunoId(requestDTO.getJogoId(), requestDTO.getAlunoId())) {
            throw new IllegalArgumentException("Este aluno já está associado a este jogo.");
        }
        
        Jogo jogo = jogoRepository.findById(requestDTO.getJogoId())
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado com ID: " + requestDTO.getJogoId()));
        Aluno aluno = alunoRepository.findById(requestDTO.getAlunoId())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com ID: " + requestDTO.getAlunoId()));
        
        JogoAluno novaAssociacao = new JogoAluno();
        novaAssociacao.setJogo(jogo);
        novaAssociacao.setAluno(aluno);
        
        jogoAlunoRepository.save(novaAssociacao);
        return "Aluno '" + aluno.getNome() + "' associado ao jogo ID " + jogo.getId() + " com sucesso.";
    }

    public void desassociar(Long jogoId, Long alunoId) {
        JogoAluno associacao = jogoAlunoRepository.findByJogoIdAndAlunoId(jogoId, alunoId)
                .orElseThrow(() -> new ResourceNotFoundException("Associação não encontrada."));
        
        jogoAlunoRepository.delete(associacao);
    }

    public List<AlunoResponseDTO> listarAlunosPorJogo(Long jogoId) {
        if (!jogoRepository.existsById(jogoId)) {
            throw new ResourceNotFoundException("Jogo não encontrado com ID: " + jogoId);
        }
        
        List<JogoAluno> associacoes = jogoAlunoRepository.findByJogoId(jogoId);
        
        return associacoes.stream()
                .map(associacao -> new AlunoResponseDTO(associacao.getAluno()))
                .collect(Collectors.toList());
    }
}