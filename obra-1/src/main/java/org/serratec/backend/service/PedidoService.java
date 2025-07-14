package org.serratec.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.backend.DTO.PedidoResponseDTO;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.enums.StatusPedido;
import org.serratec.backend.exceptions.ResourceNotFoundException;
import org.serratec.backend.repository.JogoRepository;
import org.serratec.backend.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired 
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private JogoRepository jogoRepository;

    public PedidoResponseDTO produzirPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
        pedido.setStatus(StatusPedido.CONCLUIDO);
        return new PedidoResponseDTO(pedidoRepository.save(pedido));
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