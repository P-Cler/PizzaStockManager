package org.serratec.backend.DTO;

import java.util.List;

public class ReceitaRequestDTO {
    private String nome;
    private List<ReceitaIngredienteRequestDTO> ingredientes;
    
    private List<Long> receitasIds;
    
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<ReceitaIngredienteRequestDTO> getIngredientes() {
		return ingredientes;
	}
	public void setIngredientes(List<ReceitaIngredienteRequestDTO> ingredientes) {
		this.ingredientes = ingredientes;
	}
	public List<Long> getReceitasIds() {
		return receitasIds;
	}
	public void setReceitasIds(List<Long> receitasIds) {
		this.receitasIds = receitasIds;
	}
    
    
    
}