package org.serratec.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.serratec.backend.DTO.PedidoResponseDTO;
import org.serratec.backend.entity.EstoqueJogo;
import org.serratec.backend.entity.Jogo;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.entity.Receita;
import org.serratec.backend.entity.ReceitaIngrediente;
import org.serratec.backend.enums.StatusPedido;
import org.serratec.backend.exceptions.ResourceNotFoundException;
import org.serratec.backend.repository.JogoRepository;
import org.serratec.backend.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;

@Service
public class PedidoService {

    @Autowired 
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private JogoRepository jogoRepository;

    @Transactional
    public PedidoResponseDTO produzirPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + pedidoId));

        if (pedido.getStatus() != StatusPedido.PENDENTE) {
            throw new ValidationException("Este pedido não está pendente e não pode ser produzido. Status atual: " + pedido.getStatus());
        }

        Jogo jogo = pedido.getJogo();
        int quantidadePizzas = pedido.getQuantidadePizzas();
        
        if (jogo.getReceitas() == null || jogo.getReceitas().isEmpty()) {
            throw new ValidationException("O jogo não possui receita para produzir o pedido.");
        }
        Receita receita = jogo.getReceitas().get(0).getReceita();
        List<ReceitaIngrediente> ingredientesDaReceita = receita.getIngredientes();

        Map<Long, EstoqueJogo> mapaEstoque = jogo.getEstoque().stream()
                .collect(Collectors.toMap(e -> e.getIngrediente().getId(), e -> e));

        for (ReceitaIngrediente ingredienteNecessario : ingredientesDaReceita) {
            Long idIngrediente = ingredienteNecessario.getIngrediente().getId();
            BigDecimal totalNecessario = ingredienteNecessario.getQuantidade().multiply(new BigDecimal(quantidadePizzas));
            
            EstoqueJogo itemEmEstoque = mapaEstoque.get(idIngrediente);

            if (itemEmEstoque == null) {
                throw new ValidationException("Estoque para o ingrediente '" + ingredienteNecessario.getIngrediente().getNome() + "' não existe no jogo.");
            }

            if (itemEmEstoque.getEstoqueAtual().compareTo(totalNecessario) < 0) {
                throw new ValidationException("Estoque insuficiente para o ingrediente '" + ingredienteNecessario.getIngrediente().getNome() + 
                                              "'. Necessário: " + totalNecessario + ", Disponível: " + itemEmEstoque.getEstoqueAtual());
            }
        }
        
        for (ReceitaIngrediente ingredienteNecessario : ingredientesDaReceita) {
            Long idIngrediente = ingredienteNecessario.getIngrediente().getId();
            BigDecimal totalADebitar = ingredienteNecessario.getQuantidade().multiply(new BigDecimal(quantidadePizzas));

            EstoqueJogo itemEmEstoque = mapaEstoque.get(idIngrediente);
            itemEmEstoque.setEstoqueAtual(itemEmEstoque.getEstoqueAtual().subtract(totalADebitar));
        }

        pedido.setStatus(StatusPedido.CONCLUIDO);
        
        pedidoRepository.save(pedido);

        return new PedidoResponseDTO(pedido);
    }

    public PedidoResponseDTO rejeitarPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
        pedido.setStatus(StatusPedido.REJEITADO);
        return new PedidoResponseDTO(pedidoRepository.save(pedido));
    }
    
    public PedidoResponseDTO buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .map(PedidoResponseDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id));
    }

    public List<PedidoResponseDTO> listarPorJogo(Long jogoId) {
        if (!jogoRepository.existsById(jogoId)) {
            throw new ResourceNotFoundException("Jogo não encontrado com ID: " + jogoId);
        }
        return pedidoRepository.findByJogoId(jogoId).stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.findAll().stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }
    
}