package org.serratec.backend.DTO;

import java.math.BigDecimal;

import org.serratec.backend.entity.EstoqueJogo;
import org.serratec.backend.entity.LoteEstoque;

public class EstoqueResponseDTO {

    private Long id;
    private Long jogoId;
    private Long ingredienteId;
    private String nomeIngrediente; 
    private BigDecimal estoqueAtual;
    private BigDecimal estoqueMinimo;
    private BigDecimal estoqueMax;
    private int leadTime;
    private BigDecimal pontoPedido;
    private int validadeEmCiclos;
    private Integer cicloDeVencimentoMaisProximo;
    

    public EstoqueResponseDTO(EstoqueJogo estoque) {
        this.id = estoque.getId();
        this.estoqueAtual = estoque.getEstoqueAtual();
        this.estoqueMinimo = estoque.getEstoqueMinimo();
        this.estoqueMax = estoque.getEstoqueMax();
        this.leadTime = estoque.getLeadTime();
        this.pontoPedido = estoque.getPontoPedido();
        this.validadeEmCiclos = estoque.getValidadeEmCiclos();


        if (estoque.getJogo() != null) {
            this.jogoId = estoque.getJogo().getId();
        }
        if (estoque.getIngrediente() != null) {
            this.ingredienteId = estoque.getIngrediente().getId();
            this.nomeIngrediente = estoque.getIngrediente().getNome();
        }
        
        if (estoque.getLotes() != null && !estoque.getLotes().isEmpty()) {
            this.cicloDeVencimentoMaisProximo = estoque.getLotes().stream()
                .mapToInt(LoteEstoque::getCicloDeExpiracao)
                .min()
                .orElse(0); 
        }
        
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJogoId() {
		return jogoId;
	}

	public void setJogoId(Long jogoId) {
		this.jogoId = jogoId;
	}

	public Long getIngredienteId() {
		return ingredienteId;
	}

	public void setIngredienteId(Long ingredienteId) {
		this.ingredienteId = ingredienteId;
	}

	public String getNomeIngrediente() {
		return nomeIngrediente;
	}

	public void setNomeIngrediente(String nomeIngrediente) {
		this.nomeIngrediente = nomeIngrediente;
	}

	public BigDecimal getEstoqueAtual() {
		return estoqueAtual;
	}

	public void setEstoqueAtual(BigDecimal estoqueAtual) {
		this.estoqueAtual = estoqueAtual;
	}

	public BigDecimal getEstoqueMinimo() {
		return estoqueMinimo;
	}

	public void setEstoqueMinimo(BigDecimal estoqueMinimo) {
		this.estoqueMinimo = estoqueMinimo;
	}

	public BigDecimal getEstoqueMax() {
		return estoqueMax;
	}

	public void setEstoqueMax(BigDecimal estoqueMax) {
		this.estoqueMax = estoqueMax;
	}

	public int getLeadTime() {
		return leadTime;
	}

	public void setLeadTime(int leadTime) {
		this.leadTime = leadTime;
	}

	public BigDecimal getPontoPedido() {
		return pontoPedido;
	}

	public void setPontoPedido(BigDecimal pontoPedido) {
		this.pontoPedido = pontoPedido;
	}

	public int getValidadeEmCiclos() {
		return validadeEmCiclos;
	}

	public void setValidadeEmCiclos(int validadeEmCiclos) {
		this.validadeEmCiclos = validadeEmCiclos;
	}

	public Integer getCicloDeVencimentoMaisProximo() {
		return cicloDeVencimentoMaisProximo;
	}

	public void setCicloDeVencimentoMaisProximo(Integer cicloDeVencimentoMaisProximo) {
		this.cicloDeVencimentoMaisProximo = cicloDeVencimentoMaisProximo;
	}
	
	
    
}