package org.serratec.backend.DTO;

import java.math.BigDecimal;

public class IngredienteLimitanteDTO implements Comparable<IngredienteLimitanteDTO> {

    private Long ingredienteId;
    private String nomeIngrediente;
    private int capacidadeProducaoIndividual;
    private BigDecimal quantidadeParaProximaPizza; 

    
    
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



	public int getCapacidadeProducaoIndividual() {
		return capacidadeProducaoIndividual;
	}



	public void setCapacidadeProducaoIndividual(int capacidadeProducaoIndividual) {
		this.capacidadeProducaoIndividual = capacidadeProducaoIndividual;
	}



	public BigDecimal getQuantidadeParaProximaPizza() {
		return quantidadeParaProximaPizza;
	}



	public void setQuantidadeParaProximaPizza(BigDecimal quantidadeParaProximaPizza) {
		this.quantidadeParaProximaPizza = quantidadeParaProximaPizza;
	}



	@Override
    public int compareTo(IngredienteLimitanteDTO other) {
        int comparacaoCapacidade = Integer.compare(this.capacidadeProducaoIndividual, other.capacidadeProducaoIndividual);
        if (comparacaoCapacidade != 0) {
            return comparacaoCapacidade;
        }
        return other.quantidadeParaProximaPizza.compareTo(this.quantidadeParaProximaPizza);
    }
}