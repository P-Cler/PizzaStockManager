package org.serratec.backend.service;

import java.util.List;
import java.util.stream.Collectors;
import org.serratec.backend.DTO.IngredienteRequestDTO;
import org.serratec.backend.DTO.IngredienteResponseDTO;
import org.serratec.backend.entity.Ingrediente;
import org.serratec.backend.exceptions.ResourceNotFoundException;
import org.serratec.backend.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    public IngredienteResponseDTO criar(IngredienteRequestDTO requestDTO) {
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNome(requestDTO.getNome());
        ingrediente.setUnidade(requestDTO.getUnidade());

        Ingrediente ingredienteSalvo = ingredienteRepository.save(ingrediente);
        return new IngredienteResponseDTO(ingredienteSalvo);
    }

    public IngredienteResponseDTO buscarPorId(Long id) {
        return ingredienteRepository.findById(id)
                .map(IngredienteResponseDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Ingrediente não encontrado com o ID: " + id));
    }

    public List<IngredienteResponseDTO> listarTodos() {
        return ingredienteRepository.findAll().stream()
                .map(IngredienteResponseDTO::new)
                .collect(Collectors.toList());
    }

    public IngredienteResponseDTO atualizar(Long id, IngredienteRequestDTO requestDTO) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingrediente não encontrado com o ID: " + id));
        
        ingrediente.setNome(requestDTO.getNome());
        ingrediente.setUnidade(requestDTO.getUnidade());
        
        ingredienteRepository.save(ingrediente);
        return new IngredienteResponseDTO(ingrediente);
    }

    public void deletar(Long id) {
        if (!ingredienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ingrediente não encontrado com o ID: " + id);
        }
        ingredienteRepository.deleteById(id);
    }
    
    public List<IngredienteResponseDTO> criarVarios(List<IngredienteRequestDTO> ingredientesRequest) {
        List<Ingrediente> ingredientes = ingredientesRequest.stream()
            .map(dto -> {
                Ingrediente ingrediente = new Ingrediente();
                ingrediente.setNome(dto.getNome());
                ingrediente.setUnidade(dto.getUnidade());
                return ingrediente;
            })
            .collect(Collectors.toList());

        List<Ingrediente> ingredientesSalvos = ingredienteRepository.saveAll(ingredientes);

        return ingredientesSalvos.stream()
            .map(IngredienteResponseDTO::new)
            .collect(Collectors.toList());
    }

    
}