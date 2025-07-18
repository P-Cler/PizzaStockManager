package org.serratec.backend.DTO;

import java.math.BigDecimal;

import org.serratec.backend.entity.ResultadoJogo;

public class ResultadoJogoDTO {
    private Long jogoId;
    private int pontuacaoFinal;

    private int totalPedidosProduzidos;
    private int totalPedidosRejeitados;
    private int totalPizzasProduzidas;
    private int totalPizzasRejeitadas;
    private int totalEntregasPedidas;
    private BigDecimal totalEstoqueExcedente;
    private int totalPontosPerdidosFaltaEstoque;
    private int totalPontosPerdidosEstoquePerdido;
    private BigDecimal totalEstoquesPerdidos;

    public ResultadoJogoDTO(ResultadoJogo resultado) {
        this.jogoId = resultado.getJogo().getId();
        this.pontuacaoFinal = resultado.getPontuacaoFinal();
        this.totalPedidosProduzidos = resultado.getTotalPedidosProduzidos();
        this.totalPedidosRejeitados = resultado.getTotalPedidosRejeitados();
        this.totalPizzasProduzidas = resultado.getTotalPizzasProduzidas();
        this.totalPizzasRejeitadas = resultado.getTotalPizzasRejeitadas();
        this.totalEntregasPedidas = resultado.getTotalEntregasPedidas();
        this.totalEstoqueExcedente = resultado.getTotalEstoqueExcedente();
        this.totalPontosPerdidosFaltaEstoque = resultado.getTotalPontosPerdidosFaltaEstoque();
        this.totalPontosPerdidosEstoquePerdido = resultado.getTotalPontosPerdidosEstoquePerdido();
        this.totalEstoquesPerdidos = resultado.getTotalEstoquesPerdidos();
    }

	public Long getJogoId() {
		return jogoId;
	}

	public int getPontuacaoFinal() {
		return pontuacaoFinal;
	}

	public int getTotalPedidosProduzidos() {
		return totalPedidosProduzidos;
	}

	public int getTotalPedidosRejeitados() {
		return totalPedidosRejeitados;
	}

	public int getTotalPizzasProduzidas() {
		return totalPizzasProduzidas;
	}

	public int getTotalPizzasRejeitadas() {
		return totalPizzasRejeitadas;
	}

	public int getTotalEntregasPedidas() {
		return totalEntregasPedidas;
	}

	public BigDecimal getTotalEstoqueExcedente() {
		return totalEstoqueExcedente;
	}

	public int getTotalPontosPerdidosFaltaEstoque() {
		return totalPontosPerdidosFaltaEstoque;
	}

	public int getTotalPontosPerdidosEstoquePerdido() {
		return totalPontosPerdidosEstoquePerdido;
	}

	public BigDecimal getTotalEstoquesPerdidos() {
		return totalEstoquesPerdidos;
	}
    
}