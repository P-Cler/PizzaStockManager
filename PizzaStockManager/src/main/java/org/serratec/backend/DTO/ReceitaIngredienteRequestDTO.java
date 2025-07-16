package org.serratec.backend.DTO;

import java.math.BigDecimal;

public class ReceitaIngredienteRequestDTO {
    private Long ingredienteId;
    private BigDecimal quantidade;
    private int tempoPreparo;
	public Long getIngredienteId() {
		return ingredienteId;
	}
	public void setIngredienteId(Long ingredienteId) {
		this.ingredienteId = ingredienteId;
	}
	public BigDecimal getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}
	public int getTempoPreparo() {
		return tempoPreparo;
	}
	public void setTempoPreparo(int tempoPreparo) {
		this.tempoPreparo = tempoPreparo;
	}

    
    
}