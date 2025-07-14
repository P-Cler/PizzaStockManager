package org.serratec.backend.entity;

import java.math.BigDecimal;
import org.serratec.backend.enums.StatusEntrega;
import jakarta.persistence.*;

@Entity
public class Entrega {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "estoque_jogo_id", nullable = false)
    private EstoqueJogo estoqueJogo;

    @Enumerated(EnumType.STRING)
    private StatusEntrega status;

    @Column(nullable = false)
    private BigDecimal quantidadePedida;

    private int cicloDoPedido;

    private int cicloDisponivel;

    private BigDecimal estoqueAntesDoRecebimento;
    private BigDecimal estoqueDepoisDoRecebimento;
    private BigDecimal quantidadeExcedente;
    private Integer cicloDoRecebimento;
    
    
    
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
	public StatusEntrega getStatus() {
		return status;
	}
	public void setStatus(StatusEntrega status) {
		this.status = status;
	}
	public BigDecimal getQuantidadePedida() {
		return quantidadePedida;
	}
	public void setQuantidadePedida(BigDecimal quantidadePedida) {
		this.quantidadePedida = quantidadePedida;
	}
	public int getCicloDoPedido() {
		return cicloDoPedido;
	}
	public void setCicloDoPedido(int cicloDoPedido) {
		this.cicloDoPedido = cicloDoPedido;
	}
	public int getCicloDisponivel() {
		return cicloDisponivel;
	}
	public void setCicloDisponivel(int cicloDisponivel) {
		this.cicloDisponivel = cicloDisponivel;
	}
	public BigDecimal getEstoqueAntesDoRecebimento() {
		return estoqueAntesDoRecebimento;
	}
	public void setEstoqueAntesDoRecebimento(BigDecimal estoqueAntesDoRecebimento) {
		this.estoqueAntesDoRecebimento = estoqueAntesDoRecebimento;
	}
	public BigDecimal getEstoqueDepoisDoRecebimento() {
		return estoqueDepoisDoRecebimento;
	}
	public void setEstoqueDepoisDoRecebimento(BigDecimal estoqueDepoisDoRecebimento) {
		this.estoqueDepoisDoRecebimento = estoqueDepoisDoRecebimento;
	}
	public BigDecimal getQuantidadeExcedente() {
		return quantidadeExcedente;
	}
	public void setQuantidadeExcedente(BigDecimal quantidadeExcedente) {
		this.quantidadeExcedente = quantidadeExcedente;
	}
	public Integer getCicloDoRecebimento() {
		return cicloDoRecebimento;
	}
	public void setCicloDoRecebimento(Integer cicloDoRecebimento) {
		this.cicloDoRecebimento = cicloDoRecebimento;
	}

    
    
}