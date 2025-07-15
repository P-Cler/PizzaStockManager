package org.serratec.backend.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.serratec.backend.DTO.EstoqueJogoResponseDTO;
import org.serratec.backend.DTO.EstoqueRequestDTO;
import org.serratec.backend.DTO.EstoqueResponseDTO;
import org.serratec.backend.DTO.EstoqueUpdateRequestDTO;
import org.serratec.backend.entity.EstoqueJogo;
import org.serratec.backend.entity.Ingrediente;
import org.serratec.backend.entity.Jogo;
import org.serratec.backend.entity.Receita;
import org.serratec.backend.entity.ReceitaIngrediente;
import org.serratec.backend.exceptions.ResourceNotFoundException;
import org.serratec.backend.repository.EstoqueRepository;
import org.serratec.backend.repository.IngredienteRepository;
import org.serratec.backend.repository.JogoReceitaRepository;
import org.serratec.backend.repository.JogoRepository;
import org.serratec.backend.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;
    
    @Autowired private ReceitaRepository receitaRepository;
    
    @Autowired private JogoReceitaRepository jogoReceitaRepository;

    public EstoqueResponseDTO criarEstoque(EstoqueRequestDTO requestDTO) {
        Jogo jogo = jogoRepository.findById(requestDTO.getJogoId())
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado com o ID: " + requestDTO.getJogoId()));

        Ingrediente ingrediente = ingredienteRepository.findById(requestDTO.getIngredienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Ingrediente não encontrado com o ID: " + requestDTO.getIngredienteId()));

        EstoqueJogo novoEstoque = new EstoqueJogo();
        novoEstoque.setJogo(jogo);
        novoEstoque.setIngrediente(ingrediente);
        novoEstoque.setEstoqueAtual(requestDTO.getEstoqueAtual());
        novoEstoque.setEstoqueMinimo(requestDTO.getEstoqueMinimo());
        novoEstoque.setEstoqueMax(requestDTO.getEstoqueMax());
        novoEstoque.setLeadTime(requestDTO.getLeadTime());
        novoEstoque.setPontoPedido(requestDTO.getPontoPedido());

        validarValoresEstoque(novoEstoque);
        
        EstoqueJogo estoqueSalvo = estoqueRepository.save(novoEstoque);

        return new EstoqueResponseDTO(estoqueSalvo);
    }
    
    public EstoqueJogoResponseDTO listarPorJogoId(Long jogoId){
        if(!jogoRepository.existsById(jogoId)) {
            throw new ResourceNotFoundException("Não foi encontrado nenhum estoque com o id do jogo" + jogoId);
        }
        
        Jogo jogo = jogoRepository.findById(jogoId).get();
        
        int producaoTotal = calcularQuantidadeProduzida(jogo);
        
        List<EstoqueResponseDTO> itensDeEstoqueDTO = jogo.getEstoque().stream()
                .map(EstoqueResponseDTO::new)
                .collect(Collectors.toList());

        EstoqueJogoResponseDTO response = new EstoqueJogoResponseDTO();
        response.setQuantidadeProduzidaTotal(producaoTotal);
        response.setItens(itensDeEstoqueDTO);
        
        return response;
    }
    
    @Transactional
    public List<EstoqueResponseDTO> adicionarEstoquePorReceita(Long jogoId, Long receitaId) {
        Jogo jogo = jogoRepository.findById(jogoId)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado com ID: " + jogoId));
        Receita receita = receitaRepository.findById(receitaId)
                .orElseThrow(() -> new ResourceNotFoundException("Receita não encontrada com ID: " + receitaId));

        if (!jogoReceitaRepository.existsByJogoIdAndReceitaId(jogoId, receitaId)) {
            throw new IllegalStateException("ERRO: A receita '" + receita.getNome() + "' não está associada ao jogo " + jogoId);
        }

        List<EstoqueJogo> estoquesParaSalvar = new ArrayList<>();

        for (ReceitaIngrediente ingredienteDaReceita : receita.getIngredientes()) {
            boolean estoqueJaExiste = estoqueRepository.existsByJogoIdAndIngredienteId(
                    jogo.getId(),
                    ingredienteDaReceita.getIngrediente().getId()
            );

            if (!estoqueJaExiste) {
                EstoqueJogo novoEstoque = new EstoqueJogo();
                novoEstoque.setJogo(jogo);
                novoEstoque.setIngrediente(ingredienteDaReceita.getIngrediente());
                
                novoEstoque.setEstoqueAtual(BigDecimal.valueOf(10.0));
                novoEstoque.setEstoqueMinimo(BigDecimal.valueOf(10.0)); 
                novoEstoque.setEstoqueMax(BigDecimal.valueOf(100.0));  
                novoEstoque.setLeadTime(2);                             
                novoEstoque.setPontoPedido(BigDecimal.valueOf(20.0)); 

                validarValoresEstoque(novoEstoque);
                
                estoquesParaSalvar.add(novoEstoque);
            }
        }

        if (estoquesParaSalvar.isEmpty()) {
            return Collections.emptyList();
        }
        
        

        List<EstoqueJogo> estoquesSalvos = estoqueRepository.saveAll(estoquesParaSalvar);

        return estoquesSalvos.stream()
                .map(EstoqueResponseDTO::new)
                .collect(Collectors.toList());
    }
    
    
    public EstoqueResponseDTO atualizarEstoque(Long idEstoque, EstoqueUpdateRequestDTO updateDTO) {
        EstoqueJogo estoqueParaAtualizar = estoqueRepository.findById(idEstoque)
                .orElseThrow(() -> new ResourceNotFoundException("Item de estoque não encontrado com o ID: " + idEstoque));

        estoqueParaAtualizar.setEstoqueAtual(updateDTO.getEstoqueAtual());
        estoqueParaAtualizar.setEstoqueMinimo(updateDTO.getEstoqueMinimo());
        estoqueParaAtualizar.setEstoqueMax(updateDTO.getEstoqueMax());
        estoqueParaAtualizar.setLeadTime(updateDTO.getLeadTime());
        estoqueParaAtualizar.setPontoPedido(updateDTO.getPontoPedido());
        
        validarValoresEstoque(estoqueParaAtualizar);
        
        EstoqueJogo estoqueSalvo = estoqueRepository.save(estoqueParaAtualizar);

        return new EstoqueResponseDTO(estoqueSalvo);
    }
    
    
    
    public EstoqueResponseDTO atualizarCampoEspecifico(Long jogoId, Long ingredienteId, int campoId, String novoValor) {
        
        EstoqueJogo estoqueParaAtualizar = estoqueRepository.findByJogoIdAndIngredienteId(jogoId, ingredienteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Estoque para o Jogo ID " + jogoId + " e Ingrediente ID " + ingredienteId + " não foi encontrado."
                ));

        try {
            switch (campoId) {
                case 1: // Estoque Atual
                    estoqueParaAtualizar.setEstoqueAtual(new BigDecimal(novoValor));
                    break;
                case 2: // Estoque Mínimo
                    estoqueParaAtualizar.setEstoqueMinimo(new BigDecimal(novoValor));
                    break;
                case 3: // Estoque Máximo
                    estoqueParaAtualizar.setEstoqueMax(new BigDecimal(novoValor));
                    break;
                case 4: // Lead Time
                    estoqueParaAtualizar.setLeadTime(Integer.parseInt(novoValor));
                    break;
                case 5: // Ponto de Pedido
                    estoqueParaAtualizar.setPontoPedido(new BigDecimal(novoValor));
                    break;
                default:
                    throw new IllegalArgumentException("ID de campo inválido: " + campoId + ". Use de 1 a 5.");
            }

            validarValoresEstoque(estoqueParaAtualizar); 

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O valor '" + novoValor + "' não é válido para o campo selecionado.", e);
        }

        EstoqueJogo estoqueSalvo = estoqueRepository.save(estoqueParaAtualizar);

        return new EstoqueResponseDTO(estoqueSalvo);
    }
    
    
    private void validarValoresEstoque(EstoqueJogo estoque) {
        if (estoque.getEstoqueAtual().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Estoque atual não pode ser negativo.");
        }
        if (estoque.getEstoqueMinimo().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Estoque mínimo não pode ser negativo.");
        }
        if (estoque.getEstoqueMax().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Estoque máximo não pode ser negativo.");
        }
        if (estoque.getPontoPedido().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Ponto de pedido não pode ser negativo.");
        }
        if (estoque.getLeadTime() < 0) {
            throw new IllegalArgumentException("Lead time não pode ser negativo.");
        }

        if (estoque.getEstoqueMax().compareTo(estoque.getEstoqueMinimo()) < 0) {
            throw new IllegalArgumentException("O estoque máximo não pode ser menor que o estoque mínimo.");
        }

        if (estoque.getEstoqueAtual().compareTo(estoque.getEstoqueMax()) > 0) {
            throw new IllegalArgumentException("O estoque atual não pode ser maior que o estoque máximo.");
        }
    }
    
    private int calcularQuantidadeProduzida(Jogo jogo) {
        if (jogo.getReceitas() == null || jogo.getReceitas().isEmpty()) {
            return 0;
        }

        Receita receita = jogo.getReceitas().get(0).getReceita();
        List<ReceitaIngrediente> ingredientesDaReceita = receita.getIngredientes();

        if (ingredientesDaReceita.isEmpty()) {
            return 0;
        }

        Map<Long, EstoqueJogo> mapaEstoque = jogo.getEstoque().stream()
                .collect(Collectors.toMap(e -> e.getIngrediente().getId(), e -> e));

        int gargaloProducao = Integer.MAX_VALUE; 

        for (ReceitaIngrediente ingredienteNecessario : ingredientesDaReceita) {
            Long idIngrediente = ingredienteNecessario.getIngrediente().getId();
            BigDecimal quantidadeNecessaria = ingredienteNecessario.getQuantidade();

            if (quantidadeNecessaria.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }

            EstoqueJogo itemEmEstoque = mapaEstoque.get(idIngrediente);

            if (itemEmEstoque == null) {
                return 0; 
            }

            int producaoPossivelComEsteItem = itemEmEstoque.getEstoqueAtual()
                    .divide(quantidadeNecessaria, 0, RoundingMode.FLOOR)
                    .intValue();
            
            gargaloProducao = Math.min(gargaloProducao, producaoPossivelComEsteItem);
        }

        return gargaloProducao == Integer.MAX_VALUE ? 0 : gargaloProducao;
    }
    
}