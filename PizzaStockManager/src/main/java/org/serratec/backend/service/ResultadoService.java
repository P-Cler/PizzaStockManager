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
        int pedidosProduzidos = 0;
        int pedidosRejeitados = 0;
        int pizzasProduzidas = 0;
        int pizzasRejeitadas = 0;
        
        BigDecimal estoqueExcedenteTotal = BigDecimal.ZERO;
        System.out.println("Verificando " + jogo.getPedidos().size() + " pedidos.");

        for (Pedido pedido : jogo.getPedidos()) {
            if (pedido.getStatus() == StatusPedido.CONCLUIDO) {
                pedidosProduzidos++;
                pizzasProduzidas += pedido.getQuantidadePizzas();
                System.out.println("--- Quantidade de pizzas produzidas: " + pedido.getQuantidadePizzas() + " ---");
                System.out.println("--- Pontos por pizza produzida: " + jogo.getPontosPorPizzaProduzida() + " ---");
                System.out.println("--- PONTUAÇÃO antes do calculo: " + pontuacao + " ---");

                pontuacao += pedido.getQuantidadePizzas() * jogo.getPontosPorPizzaProduzida();
                System.out.println("--- PONTUAÇÃO depois do calculo: " + pontuacao + " ---");

            } else if (pedido.getStatus() == StatusPedido.REJEITADO) {
                pedidosRejeitados++;
                pizzasRejeitadas += pedido.getQuantidadePizzas();
                System.out.println("--- Quantidade de pizzas rejeitadas: " + pedido.getQuantidadePizzas() + " ---");
                System.out.println("--- Pontos por pizza rejeitadas: " + jogo.getPontosPerdidosPorPizzaRejeitada() + " ---");
                System.out.println("--- PONTUAÇÃO antes do calculo: " + pontuacao + " ---");

                pontuacao -= pedido.getQuantidadePizzas() * jogo.getPontosPerdidosPorPizzaRejeitada();
                System.out.println("--- PONTUAÇÃO depois do calculo: " + pontuacao + " ---");

            }
        }
        

        List<Entrega> entregas = entregaRepository.findByEstoqueJogo_Jogo_Id(jogo.getId());
        for (Entrega entrega : entregas) {
            if (entrega.getQuantidadeExcedente() != null && entrega.getQuantidadeExcedente().compareTo(BigDecimal.ZERO) > 0) {
                estoqueExcedenteTotal = estoqueExcedenteTotal.add(entrega.getQuantidadeExcedente());

                BigDecimal pontosPerdidosDecimal = entrega.getQuantidadeExcedente()
                        .multiply(new BigDecimal(jogo.getPontosPerdidosPorExcedenteEstoque()));
                System.out.println("--- Quantidade de entregas excedentes: " + pontosPerdidosDecimal.intValue() + " ---");
                System.out.println("--- Pontos por estoque excedente: " + jogo.getPontosPerdidosPorExcedenteEstoque() + " ---");
                System.out.println("--- PONTUAÇÃO antes do calculo: " + pontuacao + " ---");

                pontuacao -= pontosPerdidosDecimal.intValue();
                System.out.println("--- PONTUAÇÃO depois do calculo: " + pontuacao + " ---");

            }
        }

        System.out.println("--- Ciclos com estoque baixo: " + jogo.getTotalCiclosComEstoqueBaixo() + " ---");
        System.out.println("--- Pontos por estoque baixo: " + jogo.getPontosPerdidosPorFaltaEstoque() + " ---");
        System.out.println("--- PONTUAÇÃO antes do calculo: " + pontuacao + " ---");
        int pontosPerdidosFaltaEstoque = jogo.getTotalCiclosComEstoqueBaixo() * jogo.getPontosPerdidosPorFaltaEstoque();
        System.out.println("--- PONTUAÇÃO depois do calculo: " + pontuacao + " ---");

        pontuacao -= pontosPerdidosFaltaEstoque;
        System.out.println("Ciclos com estoque baixo: " + jogo.getTotalCiclosComEstoqueBaixo() + ". Pontos perdidos por falta de estoque: " + pontosPerdidosFaltaEstoque);

        BigDecimal totalEstoquePerdido = jogo.getTotalEstoquePerdido();
        int totalPontosPerdidosEstoquePerdido = 0; 
        System.out.println("--- PONTUAÇÃO CALCULADA: " + pontuacao + " ---");
        if (totalEstoquePerdido != null && totalEstoquePerdido.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal pontosPerdidosDecimal = totalEstoquePerdido
                    .multiply(new BigDecimal(jogo.getPontosPerdidosPorEstoquePerdido()));
            totalPontosPerdidosEstoquePerdido = pontosPerdidosDecimal.intValue();
            System.out.println("--- Estoques perdidos: " + jogo.getTotalEstoquePerdido() + " ---");
            System.out.println("--- Pontos por estoque perdido: " + jogo.getPontosPerdidosPorEstoquePerdido() + " ---");
            System.out.println("--- PONTUAÇÃO antes do calculo: " + pontuacao + " ---");
            pontuacao -= totalPontosPerdidosEstoquePerdido;
            System.out.println("--- PONTUAÇÃO depois do calculo: " + pontuacao + " ---");
        }
        
//        int pontuacaoFinal = 0;
//        
//        pontuacaoFinal += resultado.getTotalPizzasProduzidas() * jogo.getPontosPorPizzaProduzida();
//        
//        // Pontos perdidos por pizzas rejeitadas
//        pontuacaoFinal -= resultado.getTotalPizzasRejeitadas() * jogo.getPontosPerdidosPorPizzaRejeitada();
//        
//        // Pontos perdidos por excedente de estoque
//        BigDecimal pontosPerdidosExcedenteDecimal = resultado.getTotalEstoqueExcedente()
//                .multiply(new BigDecimal(jogo.getPontosPerdidosPorExcedenteEstoque()));
//        pontuacaoFinal -= pontosPerdidosExcedenteDecimal.intValue();
//
//        // Pontos perdidos por falta de estoque e por estoque perdido
//        pontuacaoFinal -= resultado.getTotalPontosPerdidosFaltaEstoque();
//        pontuacaoFinal -= resultado.getTotalPontosPerdidosEstoquePerdido();
        
        resultado.setJogo(jogo);
        resultado.setPontuacaoFinal(pontuacao); 
        resultado.setTotalPedidosProduzidos(pedidosProduzidos);
        resultado.setTotalPedidosRejeitados(pedidosRejeitados);
        resultado.setTotalPizzasProduzidas(pizzasProduzidas);
        resultado.setTotalPizzasRejeitadas(pizzasRejeitadas);
        resultado.setTotalEntregasPedidas(entregas.size());
        resultado.setTotalEstoqueExcedente(estoqueExcedenteTotal);
        resultado.setTotalPontosPerdidosFaltaEstoque(pontosPerdidosFaltaEstoque);
        
        resultado.setTotalEstoquesPerdidos(totalEstoquePerdido);
        resultado.setTotalPontosPerdidosEstoquePerdido(totalPontosPerdidosEstoquePerdido);
        
        return resultadoRepository.save(resultado);
    }
    
    public ResultadoJogoDTO buscarPorJogoId(Long jogoId) {
        ResultadoJogo resultado = resultadoRepository.findByJogoId(jogoId)
                .orElseThrow(() -> new ResourceNotFoundException("Resultado para o jogo ID " + jogoId + " não encontrado. O jogo pode não ter sido finalizado."));
        return new ResultadoJogoDTO(resultado);
    }
    
}