package org.serratec.backend.DTO;

import org.serratec.backend.entity.Ingrediente;
import org.serratec.backend.entity.Receita;
import org.serratec.backend.entity.ReceitaIngrediente;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ReceitaResponseDTO {
    private Long id;
    private String nome;
    private List<ReceitaIngredienteInfo> ingredientes;

    public static class ReceitaIngredienteInfo {
        private Long ingredienteId;
        private String nomeIngrediente;
        private BigDecimal quantidade;
        private int tempoPreparo;
        public ReceitaIngredienteInfo(ReceitaIngrediente ri) {
            Ingrediente ing = ri.getIngrediente();
            if (ing != null) {
                this.ingredienteId = ing.getId();
                this.nomeIngrediente = ing.getNome();
            }
            this.quantidade = ri.getQuantidade();
            this.tempoPreparo = ri.getTempoPreparo();
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

    public ReceitaResponseDTO(Receita receita) {
        this.id = receita.getId();
        this.nome = receita.getNome();
        if (receita.getIngredientes() != null) {
            this.ingredientes = receita.getIngredientes().stream()
                .map(ReceitaIngredienteInfo::new)
                .collect(Collectors.toList());
        }
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<ReceitaIngredienteInfo> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<ReceitaIngredienteInfo> ingredientes) {
		this.ingredientes = ingredientes;
	}
    
}