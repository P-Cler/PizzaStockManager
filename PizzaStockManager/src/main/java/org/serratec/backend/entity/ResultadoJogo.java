package org.serratec.backend.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
public class ResultadoJogo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "jogo_id", referencedColumnName = "id")
    private Jogo jogo;

    private int pontuacaoFinal;

    private int totalPedidosProduzidos;
    private int totalPedidosRejeitados;
    private int totalPizzasProduzidas;
    private int totalPizzasRejeitadas;
    private int totalEntregasPedidas;
    private BigDecimal totalEstoqueExcedente = BigDecimal.ZERO;
    private int totalPontosPerdidosFaltaEstoque;
    private int totalPontosPerdidosEstoquePerdido;
    private BigDecimal totalEstoquesPerdidos;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Jogo getJogo() {
		return jogo;
	}
	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}
	public int getPontuacaoFinal() {
		return pontuacaoFinal;
	}
	public void setPontuacaoFinal(int pontuacaoFinal) {
		this.pontuacaoFinal = pontuacaoFinal;
	}
	public int getTotalPedidosProduzidos() {
		return totalPedidosProduzidos;
	}
	public void setTotalPedidosProduzidos(int totalPedidosProduzidos) {
		this.totalPedidosProduzidos = totalPedidosProduzidos;
	}
	public int getTotalPedidosRejeitados() {
		return totalPedidosRejeitados;
	}
	public void setTotalPedidosRejeitados(int totalPedidosRejeitados) {
		this.totalPedidosRejeitados = totalPedidosRejeitados;
	}
	public int getTotalPizzasProduzidas() {
		return totalPizzasProduzidas;
	}
	public void setTotalPizzasProduzidas(int totalPizzasProduzidas) {
		this.totalPizzasProduzidas = totalPizzasProduzidas;
	}
	public int getTotalPizzasRejeitadas() {
		return totalPizzasRejeitadas;
	}
	public void setTotalPizzasRejeitadas(int totalPizzasRejeitadas) {
		this.totalPizzasRejeitadas = totalPizzasRejeitadas;
	}
	public int getTotalEntregasPedidas() {
		return totalEntregasPedidas;
	}
	public void setTotalEntregasPedidas(int totalEntregasPedidas) {
		this.totalEntregasPedidas = totalEntregasPedidas;
	}
	public BigDecimal getTotalEstoqueExcedente() {
		return totalEstoqueExcedente;
	}
	public void setTotalEstoqueExcedente(BigDecimal totalEstoqueExcedente) {
		this.totalEstoqueExcedente = totalEstoqueExcedente;
	}
	public int getTotalPontosPerdidosFaltaEstoque() {
		return totalPontosPerdidosFaltaEstoque;
	}
	public void setTotalPontosPerdidosFaltaEstoque(int totalPontosPerdidosFaltaEstoque) {
		this.totalPontosPerdidosFaltaEstoque = totalPontosPerdidosFaltaEstoque;
	}
	public int getTotalPontosPerdidosEstoquePerdido() {
		return totalPontosPerdidosEstoquePerdido;
	}
	public void setTotalPontosPerdidosEstoquePerdido(int totalPontosPerdidosEstoquePerdido2) {
		this.totalPontosPerdidosEstoquePerdido = totalPontosPerdidosEstoquePerdido2;
	}
	public BigDecimal getTotalEstoquesPerdidos() {
		return totalEstoquesPerdidos;
	}
	public void setTotalEstoquesPerdidos(BigDecimal totalEstoquePerdido) {
		this.totalEstoquesPerdidos = totalEstoquePerdido;
	}
    
    
    
}