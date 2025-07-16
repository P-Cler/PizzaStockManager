package org.serratec.backend.service;

import jakarta.transaction.Transactional;
import org.serratec.backend.DTO.ReceitaRequestDTO;
import org.serratec.backend.DTO.ReceitaResponseDTO;
import org.serratec.backend.entity.Ingrediente;
import org.serratec.backend.entity.Receita;
import org.serratec.backend.entity.ReceitaIngrediente;
import org.serratec.backend.exceptions.ResourceNotFoundException;
import org.serratec.backend.repository.IngredienteRepository;
import org.serratec.backend.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Transactional
    public ReceitaResponseDTO criar(ReceitaRequestDTO requestDTO) {
        Receita receita = new Receita();
        receita.setNome(requestDTO.getNome());

        List<ReceitaIngrediente> ingredientesDaReceita = requestDTO.getIngredientes().stream().map(ingDTO -> {
            Ingrediente ingrediente = ingredienteRepository.findById(ingDTO.getIngredienteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ingrediente não encontrado com ID: " + ingDTO.getIngredienteId()));

            ReceitaIngrediente receitaIngrediente = new ReceitaIngrediente();
            receitaIngrediente.setReceita(receita); 
            receitaIngrediente.setIngrediente(ingrediente);
            receitaIngrediente.setQuantidade(ingDTO.getQuantidade());
            receitaIngrediente.setTempoPreparo(ingDTO.getTempoPreparo());
            return receitaIngrediente;
        }).collect(Collectors.toList());

        receita.setIngredientes(ingredientesDaReceita);
        Receita receitaSalva = receitaRepository.save(receita);
        return new ReceitaResponseDTO(receitaSalva);
    }
    
    public List<ReceitaResponseDTO> listarTodas() {
        return receitaRepository.findAll().stream()
                .map(ReceitaResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ReceitaResponseDTO buscarPorId(Long id) {
        Receita receita = receitaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receita não encontrada com ID: " + id));
        return new ReceitaResponseDTO(receita);
    }
    
    @Transactional
    public ReceitaResponseDTO criarComIngredientesEReceitas(ReceitaRequestDTO requestDTO) {
        Receita receita = new Receita();
        receita.setNome(requestDTO.getNome());

        List<ReceitaIngrediente> ingredientesDiretos = requestDTO.getIngredientes().stream().map(ingDTO -> {
            Ingrediente ingrediente = ingredienteRepository.findById(ingDTO.getIngredienteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ingrediente não encontrado com ID: " + ingDTO.getIngredienteId()));

            ReceitaIngrediente receitaIngrediente = new ReceitaIngrediente();
            receitaIngrediente.setReceita(receita);
            receitaIngrediente.setIngrediente(ingrediente);
            receitaIngrediente.setQuantidade(ingDTO.getQuantidade());
            receitaIngrediente.setTempoPreparo(ingDTO.getTempoPreparo());
            return receitaIngrediente;
        }).collect(Collectors.toList());

        List<ReceitaIngrediente> ingredientesDeReceitas = requestDTO.getReceitasIds().stream()
                .flatMap(receitaId -> {
                    Receita receitaExistente = receitaRepository.findById(receitaId)
                            .orElseThrow(() -> new ResourceNotFoundException("Receita não encontrada com ID: " + receitaId));
                    return receitaExistente.getIngredientes().stream().map(ri -> {
                        ReceitaIngrediente novoRI = new ReceitaIngrediente();
                        novoRI.setReceita(receita);
                        novoRI.setIngrediente(ri.getIngrediente());
                        novoRI.setQuantidade(ri.getQuantidade());
                        novoRI.setTempoPreparo(ri.getTempoPreparo());
                        return novoRI;
                    });
                })
                .collect(Collectors.toList());

        ingredientesDiretos.addAll(ingredientesDeReceitas);
        receita.setIngredientes(ingredientesDiretos);

        Receita receitaSalva = receitaRepository.save(receita);
        return new ReceitaResponseDTO(receitaSalva);
    }

    public void deletar(Long id) {
        if (!receitaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Receita não encontrada com ID: " + id);
        }
        receitaRepository.deleteById(id);
    }
}