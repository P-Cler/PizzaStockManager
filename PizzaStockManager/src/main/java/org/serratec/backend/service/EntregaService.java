package org.serratec.backend.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.serratec.backend.DTO.EntregaRequestDTO;
import org.serratec.backend.DTO.EntregaResponseDTO;
import org.serratec.backend.entity.Entrega;
import org.serratec.backend.entity.EstoqueJogo;
import org.serratec.backend.entity.Jogo;
import org.serratec.backend.enums.JogoStatus;
import org.serratec.backend.enums.StatusEntrega;
import org.serratec.backend.exceptions.ResourceNotFoundException;
import org.serratec.backend.repository.EntregaRepository;
import org.serratec.backend.repository.EstoqueRepository;
import org.serratec.backend.repository.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;

@Service
public class EntregaService {

    @Autowired 
    private EntregaRepository entregaRepository;
    
    @Autowired 
    private EstoqueRepository estoqueRepository;
    
    @Autowired 
    private JogoRepository jogoRepository;
    
    @Autowired
    private ResultadoService resultadoService;

    @Transactional
    public EntregaResponseDTO pedirEntrega(EntregaRequestDTO requestDTO) {
        EstoqueJogo estoqueJogo = estoqueRepository.findById(requestDTO.getEstoqueId())
                .orElseThrow(() -> new ResourceNotFoundException("Item de estoque não encontrado."));
        
        Jogo jogo = estoqueJogo.getJogo();
        if (jogo.getStatus() != JogoStatus.EM_ANDAMENTO) {
            throw new ValidationException("Só é possível pedir entregas para jogos em andamento.");
        }

        int cicloAtual = calcularCicloAtual(jogo);

        Entrega novaEntrega = new Entrega();
        novaEntrega.setEstoqueJogo(estoqueJogo);
        novaEntrega.setQuantidadePedida(requestDTO.getQuantidade());
        novaEntrega.setStatus(StatusEntrega.AGUARDANDO_ENTREGA);
        novaEntrega.setCicloDoPedido(cicloAtual);
        novaEntrega.setCicloDisponivel(cicloAtual + estoqueJogo.getLeadTime());

        return new EntregaResponseDTO(entregaRepository.save(novaEntrega));
    }

    @Transactional
    public EntregaResponseDTO receberEntrega(Long entregaId) {
        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new ResourceNotFoundException("Entrega não encontrada."));

        if (entrega.getStatus() == StatusEntrega.CANCELADA) {
            throw new ValidationException("Esta entrega foi cancelada e não pode ser recebida.");
       }
        
        atualizarStatusEntrega(entrega);

        if (entrega.getStatus() != StatusEntrega.DISPONIVEL_PARA_RECEBIMENTO) {
            throw new ValidationException("Esta entrega ainda não está disponível para recebimento. Fica disponível no ciclo " + entrega.getCicloDisponivel());
        }

        EstoqueJogo estoqueJogo = entrega.getEstoqueJogo();
        BigDecimal estoqueAtual = estoqueJogo.getEstoqueAtual();
        BigDecimal estoqueMax = estoqueJogo.getEstoqueMax();
        BigDecimal espacoDisponivel = estoqueMax.subtract(estoqueAtual);

        BigDecimal quantidadeAReceber = entrega.getQuantidadePedida();
        BigDecimal quantidadeRealmenteAdicionada = quantidadeAReceber.min(espacoDisponivel);
        BigDecimal novoEstoque = estoqueAtual.add(quantidadeRealmenteAdicionada);
        BigDecimal excedente = quantidadeAReceber.subtract(quantidadeRealmenteAdicionada);

        estoqueJogo.setEstoqueAtual(novoEstoque);

        entrega.setEstoqueAntesDoRecebimento(estoqueAtual);
        entrega.setEstoqueDepoisDoRecebimento(novoEstoque);
        entrega.setQuantidadeExcedente(excedente);
        entrega.setCicloDoRecebimento(calcularCicloAtual(estoqueJogo.getJogo()));
        entrega.setStatus(StatusEntrega.CONCLUIDA);
        
        resultadoService.calcularResultados(estoqueJogo.getJogo());
        
        return new EntregaResponseDTO(entregaRepository.save(entrega));
    }

    public List<EntregaResponseDTO> listarEntregasPorJogo(Long jogoId) {
        if (!jogoRepository.existsById(jogoId)) {
            throw new ResourceNotFoundException("Jogo não encontrado.");
        }
        List<Entrega> entregas = entregaRepository.findByEstoqueJogo_Jogo_Id(jogoId);
        
        entregas.forEach(this::atualizarStatusEntrega);
        entregaRepository.saveAll(entregas);
        
        return entregas.stream().map(EntregaResponseDTO::new).collect(Collectors.toList());
    }

    private void atualizarStatusEntrega(Entrega entrega) {
        if (entrega.getStatus() == StatusEntrega.AGUARDANDO_ENTREGA) {
            Jogo jogo = entrega.getEstoqueJogo().getJogo();
            if (jogo.getStatus() == JogoStatus.EM_ANDAMENTO) {
                int cicloAtual = calcularCicloAtual(jogo);
                if (cicloAtual >= entrega.getCicloDisponivel()) {
                    entrega.setStatus(StatusEntrega.DISPONIVEL_PARA_RECEBIMENTO);
                }
            }
        }
    }
    
    @Transactional
    public EntregaResponseDTO cancelarEntrega(Long entregaId) {
        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new ResourceNotFoundException("Entrega não encontrada com ID: " + entregaId));

        if (entrega.getStatus() != StatusEntrega.AGUARDANDO_ENTREGA) {
            throw new ValidationException("Não é possível cancelar esta entrega. Status atual: " + entrega.getStatus());
        }

        entrega.setStatus(StatusEntrega.CANCELADA);
        
        return new EntregaResponseDTO(entregaRepository.save(entrega));
    }

    private int calcularCicloAtual(Jogo jogo) {
        long duracaoCicloEmSegundos = (long) jogo.getTempoTotal() * 60 / jogo.getNumeroCiclos();
        if (duracaoCicloEmSegundos <= 0) return 1;
        long segundosDesdeOInicio = Duration.between(jogo.getDataInicio(), LocalDateTime.now()).getSeconds();
        return (int) (segundosDesdeOInicio / duracaoCicloEmSegundos) + 1;
    }
}