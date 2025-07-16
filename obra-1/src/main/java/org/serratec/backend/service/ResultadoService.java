package org.serratec.backend.service;

import java.math.BigDecimal;
import java.util.List;

import org.serratec.backend.DTO.ResultadoJogoDTO;
import org.serratec.backend.entity.Entrega;
import org.serratec.backend.entity.Jogo;
import org.serratec.backend.entity.Pedido;
import org.serratec.backend.entity.ResultadoJogo;
import org.serratec.backend.enums.StatusPedido;
import org.serratec.backend.exceptions.ResourceNotFoundException;
import org.serratec.backend.repository.EntregaRepository;
import org.serratec.backend.repository.ResultadoJogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ResultadoService {

    @Autowired private ResultadoJogoRepository resultadoRepository;
    @Autowired private EntregaRepository entregaRepository;

    @Transactional
    public ResultadoJogo calcularResultados(Jogo jogo) {
    	ResultadoJogo resultado = resultadoRepository.findByJogoId(jogo.getId())
                .orElse(new ResultadoJogo());

        int pontuacao = 0;
        int pedidosProduzidos = 0, pedidosRejeitados = 0;
        int pizzasProduzidas = 0, pizzasRejeitadas = 0;
        BigDecimal estoqueExcedenteTotal = BigDecimal.ZERO;
        int pontosPerdidosFaltaEstoque = 0;
        int totalPontosPerdidosEstoquePerdido = 0, totalEstoquesPerdidos = 0;

        for (Pedido pedido : jogo.getPedidos()) {
            if (pedido.getStatus() == StatusPedido.CONCLUIDO) {
                pedidosProduzidos++;
                pizzasProduzidas += pedido.getQuantidadePizzas();
                pontuacao += pedido.getQuantidadePizzas() * jogo.getPontosPorPizzaProduzida();
            } else if (pedido.getStatus() == StatusPedido.REJEITADO) {
                pedidosRejeitados++;
                pizzasRejeitadas += pedido.getQuantidadePizzas();
                pontuacao -= pedido.getQuantidadePizzas() * jogo.getPontosPerdidosPorPizzaRejeitada();
            }
        }
        
        List<Entrega> entregas = entregaRepository.findByEstoqueJogo_Jogo_Id(jogo.getId());
        for (Entrega entrega : entregas) {
            if (entrega.getQuantidadeExcedente() != null && entrega.getQuantidadeExcedente().compareTo(BigDecimal.ZERO) > 0) {
                estoqueExcedenteTotal = estoqueExcedenteTotal.add(entrega.getQuantidadeExcedente());
                int pontosPerdidos = entrega.getQuantidadeExcedente().multiply(new BigDecimal(jogo.getPontosPerdidosPorExcedenteEstoque())).intValue();
                pontuacao -= pontosPerdidos;
            }
        }

        pontosPerdidosFaltaEstoque = jogo.getTotalCiclosComEstoqueBaixo() * jogo.getPontosPerdidosPorFaltaEstoque();
        pontuacao -= pontosPerdidosFaltaEstoque;
        
        
        resultado.setJogo(jogo);
        resultado.setPontuacaoFinal(pontuacao);
        resultado.setTotalPedidosProduzidos(pedidosProduzidos);
        resultado.setTotalPedidosRejeitados(pedidosRejeitados);
        resultado.setTotalPizzasProduzidas(pizzasProduzidas);
        resultado.setTotalPizzasRejeitadas(pizzasRejeitadas);
        resultado.setTotalEntregasPedidas(entregas.size());
        resultado.setTotalEstoqueExcedente(estoqueExcedenteTotal);
        resultado.setTotalPontosPerdidosFaltaEstoque(pontosPerdidosFaltaEstoque);
        resultado.setTotalPontosPerdidosEstoquePerdido(totalPontosPerdidosEstoquePerdido);
        resultado.setTotalEstoquesPerdidos(totalEstoquesPerdidos);
        
        return resultadoRepository.save(resultado);
    }
    
    public ResultadoJogoDTO buscarPorJogoId(Long jogoId) {
        ResultadoJogo resultado = resultadoRepository.findByJogoId(jogoId)
                .orElseThrow(() -> new ResourceNotFoundException("Resultado para o jogo ID " + jogoId + " não encontrado. O jogo pode não ter sido finalizado."));
        return new ResultadoJogoDTO(resultado);
    }
    
}