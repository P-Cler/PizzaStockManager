package org.serratec.backend.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.serratec.backend.DTO.JogoRequestDTO;
import org.serratec.backend.DTO.JogoResponseDTO;
import org.serratec.backend.entity.Jogo;
import org.serratec.backend.enums.JogoStatus;
import org.serratec.backend.repository.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;

@Service
public class JogoService {

    @Autowired
    private JogoRepository jogoRepository;
    
    @Autowired
    private JogoReceitaService jogoReceitaService;
    
    @Autowired
    private EstoqueService estoqueService;
    
    @Autowired
    private GameLogicService gameLogicService;
    
    private static final Long ID_CALABRESA = 4L;

    @Transactional
    public JogoResponseDTO criarJogo(JogoRequestDTO requestDTO) {
        Jogo jogo = new Jogo();
        jogo.setTempoTotal(requestDTO.getTempoTotal());
        jogo.setNumeroCiclos(requestDTO.getNumeroCiclos());
        jogo.setMaximoPizzas(requestDTO.getMaximoPizzas());
        jogo.setMinimoPizzas(requestDTO.getMinimoPizzas());
        Jogo jogoSalvo = jogoRepository.save(jogo);
        
        jogo.setStatus(JogoStatus.NAO_INICIADO);
        
        jogoReceitaService.associar(jogoSalvo.getId(), ID_CALABRESA);
        
        estoqueService.adicionarEstoquePorReceita(jogoSalvo.getId(), ID_CALABRESA);

        
        return new JogoResponseDTO(jogoSalvo);
    }

    
    public List<JogoResponseDTO> listarTodosJogos() {
        return jogoRepository.findAll().stream()
                .map(JogoResponseDTO::new)
                .collect(Collectors.toList());
    }

    
    public void deletarJogo(Long id) {
        if (!jogoRepository.existsById(id)) {
            throw new EntityNotFoundException("Jogo não encontrado com o ID: " + id);
        }
        jogoRepository.deleteById(id);
    }
    
    @Transactional
    public JogoResponseDTO buscarJogoPorId(Long id) {
        Jogo jogo = jogoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Jogo não encontrado com o ID: " + id));


        if (jogo.getStatus() != JogoStatus.NAO_INICIADO && jogo.getStatus() != JogoStatus.FINALIZADO) {
            
            long tempoTotalEmSegundos = (long) jogo.getTempoTotal() * 60;
            long segundosDesdeOInicioReal = Duration.between(jogo.getDataInicio(), LocalDateTime.now()).getSeconds();

            if (segundosDesdeOInicioReal >= tempoTotalEmSegundos) {
                jogo.setStatus(JogoStatus.FINALIZADO);
                return new JogoResponseDTO(jogoRepository.save(jogo)); 
            }

            if (jogo.getStatus() == JogoStatus.EM_ANDAMENTO) {
                long duracaoCicloEmSegundos = (long) jogo.getTempoTotal() * 60 / jogo.getNumeroCiclos();
                long segundosCorridosCiclo = Duration.between(jogo.getDataInicio(), LocalDateTime.now()).getSeconds();
                long cicloAtualCalculado = (segundosCorridosCiclo / duracaoCicloEmSegundos) + 1;
                
                long pedidosJaCriados = jogo.getPedidos().size();
                while (pedidosJaCriados < cicloAtualCalculado && pedidosJaCriados < jogo.getNumeroCiclos()) {
                    gameLogicService.gerarPedidoParaCiclo(jogo, pedidosJaCriados + 1);
                    pedidosJaCriados++;
                }
            }
        }
        
        return new JogoResponseDTO(jogo);
    }
    
    @Transactional
    public JogoResponseDTO atualizarCampoJogo(Long jogoId, int campoId, String novoValor) {
        Jogo jogo = jogoRepository.findById(jogoId)
                .orElseThrow(() -> new EntityNotFoundException("Jogo não encontrado com o ID: " + jogoId));

        if (jogo.getStatus() != JogoStatus.NAO_INICIADO) {
            throw new ValidationException("Parâmetros do jogo só podem ser alterados antes do seu início.");
        }

        try {
            switch (campoId) {
                case 1: // tempoTotal
                    jogo.setTempoTotal(Integer.parseInt(novoValor));
                    break;
                case 2: // numeroCiclos
                    jogo.setNumeroCiclos(Integer.parseInt(novoValor));
                    break;
                case 3: // maximoPizzas
                    jogo.setMaximoPizzas(Integer.parseInt(novoValor));
                    break;
                case 4: // minimoPizzas
                    jogo.setMinimoPizzas(Integer.parseInt(novoValor));
                    break;
                default:
                    throw new IllegalArgumentException("ID de campo inválido para Jogo: " + campoId + ". Use de 1 a 4.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O valor '" + novoValor + "' não é um número inteiro válido.", e);
        }

        Jogo jogoSalvo = jogoRepository.save(jogo);

        return new JogoResponseDTO(jogoSalvo);
    }
    
    @Transactional
    public JogoResponseDTO atualizarJogoCompleto(Long id, JogoRequestDTO requestDTO) {
        Jogo jogoExistente = jogoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Jogo não encontrado com o ID: " + id));

        if (jogoExistente.getStatus() != JogoStatus.NAO_INICIADO) {
            throw new ValidationException("Parâmetros do jogo só podem ser alterados antes do seu início.");
        }

        jogoExistente.setTempoTotal(requestDTO.getTempoTotal());
        jogoExistente.setNumeroCiclos(requestDTO.getNumeroCiclos());
        jogoExistente.setMaximoPizzas(requestDTO.getMaximoPizzas());
        jogoExistente.setMinimoPizzas(requestDTO.getMinimoPizzas());

        Jogo jogoAtualizado = jogoRepository.save(jogoExistente);
        return new JogoResponseDTO(jogoAtualizado);
    }
    
}