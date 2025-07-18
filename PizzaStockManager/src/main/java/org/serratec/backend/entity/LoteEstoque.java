package org.serratec.backend.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
public class LoteEstoque {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estoque_jogo_id", nullable = false)
    private EstoqueJogo estoqueJogo;

    private BigDecimal quantidade;
    
    private int cicloDeEntrada;
    
    private int cicloDeExpiracao;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public EstoqueJogo getEstoqueJogo() {
		return estoqueJogo;
	}
	public void setEstoqueJogo(EstoqueJogo estoqueJogo) {
		this.estoqueJogo = estoqueJogo;
	}
	public BigDecimal getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}
	public int getCicloDeEntrada() {
		return cicloDeEntrada;
	}
	public void setCicloDeEntrada(int cicloDeEntrada) {
		this.cicloDeEntrada = cicloDeEntrada;
	}
	public int getCicloDeExpiracao() {
		return cicloDeExpiracao;
	}
	public void setCicloDeExpiracao(int cicloDeExpiracao) {
		this.cicloDeExpiracao = cicloDeExpiracao;
	} 

    
    
}