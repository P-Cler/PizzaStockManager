package org.serratec.backend.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.serratec.backend.entity.Jogo;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.entity.Receita;
import org.serratec.backend.enums.JogoStatus;
import org.serratec.backend.enums.StatusPedido;
import org.serratec.backend.exceptions.ResourceNotFoundException;
import org.serratec.backend.repository.JogoRepository;
import org.serratec.backend.repository.PedidoRepository;
import org.serratec.backend.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;

@Service
public class GameLogicService {

    @Autowired private JogoRepository jogoRepository;
    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private ReceitaRepository receitaRepository;

    @Transactional
    public Jogo iniciarJogo(Long jogoId) {
    	Jogo jogo = jogoRepository.findById(jogoId)
    		    .orElseThrow(() -> new EntityNotFoundException("Jogo com ID " + jogoId + " não encontrado."));

        if (jogo.getStatus() != JogoStatus.NAO_INICIADO) {
            throw new ValidationException("Este jogo já foi iniciado ou finalizado.");
        }
        
        jogo.setStatus(JogoStatus.EM_ANDAMENTO);
        jogo.setDataInicio(LocalDateTime.now());
        
        gerarPedidoParaCiclo(jogo, 1);
        
        return jogoRepository.save(jogo);
    }

    @Transactional
    public Pedido gerarPedidoParaCiclo(Jogo jogo, long cicloNumero) {
        Receita receitaDoPedido = receitaRepository.findById(4L).get(); 

        int minPizzas = jogo.getMinimoPizzas(); 
        int maxPizzas = jogo.getMaximoPizzas();
        int quantidade = ThreadLocalRandom.current().nextInt(minPizzas, maxPizzas + 1);

        Pedido novoPedido = new Pedido();
        novoPedido.setJogo(jogo);
        novoPedido.setNomeReceita(receitaDoPedido.getNome());
        novoPedido.setQuantidadePizzas(quantidade);
        novoPedido.setDataPedido(LocalDateTime.now());
        novoPedido.setStatus(StatusPedido.PENDENTE);

        Pedido pedidoSalvo = pedidoRepository.save(novoPedido);

        String codigo = gerarCodigoCustomizado(pedidoSalvo);
        pedidoSalvo.setCodigoPedido(codigo);

        return pedidoRepository.save(pedidoSalvo);
    }

    private String gerarCodigoCustomizado(Pedido pedido) {
        long id = pedido.getId();
        long pedidosNoJogo = pedidoRepository.countByJogoId(pedido.getJogo().getId());
        long pedidosTotais = pedidoRepository.count();
        char letraAleatoria = (char) (new Random().nextInt(26) + 'A');

        return String.format("#%d%c%d%c%d", id, letraAleatoria, pedidosNoJogo, letraAleatoria, pedidosTotais);
    }
    
    @Transactional
    public Jogo pausarJogo(Long jogoId) {
        Jogo jogo = jogoRepository.findById(jogoId)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado com ID: " + jogoId));

        if (jogo.getStatus() != JogoStatus.EM_ANDAMENTO) {
            throw new ValidationException("Só é possível pausar um jogo que está em andamento.");
        }

        jogo.setStatus(JogoStatus.PAUSADO);
        jogo.setDataPausa(LocalDateTime.now());
        
        return jogoRepository.save(jogo);
    }

    
    @Transactional
    public Jogo retomarJogo(Long jogoId) {
        Jogo jogo = jogoRepository.findById(jogoId)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado com ID: " + jogoId));

        if (jogo.getStatus() != JogoStatus.PAUSADO) {
            throw new ValidationException("Só é possível retomar um jogo que está pausado.");
        }

        Duration duracaoPausa = Duration.between(jogo.getDataPausa(), LocalDateTime.now());
        
        jogo.setDataInicio(jogo.getDataInicio().plus(duracaoPausa));
        
        jogo.setStatus(JogoStatus.EM_ANDAMENTO);
        jogo.setDataPausa(null); 
        
        return jogoRepository.save(jogo);
    }
    
}