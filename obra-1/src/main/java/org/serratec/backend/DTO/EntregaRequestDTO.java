package org.serratec.backend.DTO;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class EntregaRequestDTO {
    @NotNull(message = "O ID do item de estoque é obrigatório.")
    private Long estoqueId;

    @NotNull(message = "A quantidade é obrigatória.")
    @Positive(message = "A quantidade deve ser maior que zero.")
    private BigDecimal quantidade;

	public Long getEstoqueId() {
		return estoqueId;
	}

	public void setEstoqueId(Long estoqueId) {
		this.estoqueId = estoqueId;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}
    
    
}