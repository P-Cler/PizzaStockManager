package org.serratec.backend.DTO;

import java.math.BigDecimal;

public class EstoqueReceitaRequestDTO {
    private BigDecimal estoqueMinimoPadrao;
    private BigDecimal estoqueMaxPadrao;
    private int leadTimePadrao;
    private BigDecimal pontoPedidoPadrao;
	public BigDecimal getEstoqueMinimoPadrao() {
		return estoqueMinimoPadrao;
	}
	public void setEstoqueMinimoPadrao(BigDecimal estoqueMinimoPadrao) {
		this.estoqueMinimoPadrao = estoqueMinimoPadrao;
	}
	public BigDecimal getEstoqueMaxPadrao() {
		return estoqueMaxPadrao;
	}
	public void setEstoqueMaxPadrao(BigDecimal estoqueMaxPadrao) {
		this.estoqueMaxPadrao = estoqueMaxPadrao;
	}
	public int getLeadTimePadrao() {
		return leadTimePadrao;
	}
	public void setLeadTimePadrao(int leadTimePadrao) {
		this.leadTimePadrao = leadTimePadrao;
	}
	public BigDecimal getPontoPedidoPadrao() {
		return pontoPedidoPadrao;
	}
	public void setPontoPedidoPadrao(BigDecimal pontoPedidoPadrao) {
		this.pontoPedidoPadrao = pontoPedidoPadrao;
	}
    
    
    
}