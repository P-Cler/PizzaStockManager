package org.serratec.backend.service;

import java.util.List;
import java.util.stream.Collectors;
import org.serratec.backend.DTO.AlunoRequestDTO;
import org.serratec.backend.DTO.AlunoResponseDTO;
import org.serratec.backend.entity.Aluno;
import org.serratec.backend.exceptions.ResourceNotFoundException;
import org.serratec.backend.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    
    public AlunoResponseDTO criar(AlunoRequestDTO requestDTO) {
        Aluno aluno = new Aluno();
        aluno.setNome(requestDTO.getNome());
        alunoRepository.save(aluno);
        return new AlunoResponseDTO(aluno);
    }

    
    public List<AlunoResponseDTO> listarTodos() {
        return alunoRepository.findAll().stream()
                .map(AlunoResponseDTO::new)
                .collect(Collectors.toList());
    }

    
    public AlunoResponseDTO buscarPorId(Long id) {
        return alunoRepository.findById(id)
                .map(AlunoResponseDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com o ID: " + id));
    }

    
    public AlunoResponseDTO atualizar(Long id, AlunoRequestDTO requestDTO) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com o ID: " + id));
        
        aluno.setNome(requestDTO.getNome());
        alunoRepository.save(aluno);
        
        return new AlunoResponseDTO(aluno);
    }

    
    public void deletar(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Aluno não encontrado com o ID: " + id);
        }
        alunoRepository.deleteById(id);
    }
}