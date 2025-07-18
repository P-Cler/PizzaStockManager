package org.serratec.backend.DTO;

import java.util.List;

public class EstoqueJogoResponseDTO {

    private int quantidadeProduzidaTotal;
    private List<EstoqueResponseDTO> itens;
    
    private List<IngredienteLimitanteDTO> ingredientesLimitantes;


    public int getQuantidadeProduzidaTotal() {
        return quantidadeProduzidaTotal;
    }

    public void setQuantidadeProduzidaTotal(int quantidadeProduzidaTotal) {
        this.quantidadeProduzidaTotal = quantidadeProduzidaTotal;
    }

    public List<EstoqueResponseDTO> getItens() {
        return itens;
    }

    public void setItens(List<EstoqueResponseDTO> itens) {
        this.itens = itens;
    }

	public List<IngredienteLimitanteDTO> getIngredientesLimitantes() {
		return ingredientesLimitantes;
	}

	public void setIngredientesLimitantes(List<IngredienteLimitanteDTO> ingredientesLimitantes) {
		this.ingredientesLimitantes = ingredientesLimitantes;
	}
    
    
    
}