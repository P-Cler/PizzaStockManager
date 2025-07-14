package org.serratec.backend.DTO;

import java.math.BigDecimal;
import org.serratec.backend.entity.Entrega;
import org.serratec.backend.enums.StatusEntrega;

public class EntregaResponseDTO {
    private Long id;
    private Long estoqueId;
    private String nomeIngrediente;
    private StatusEntrega status;
    private BigDecimal quantidadePedida;
    private int cicloDoPedido;
    private int cicloDisponivel;
    private BigDecimal estoqueAntesDoRecebimento;
    private BigDecimal estoqueDepoisDoRecebimento;
    private BigDecimal quantidadeExcedente;
    private Integer cicloDoRecebimento;

    public EntregaResponseDTO(Entrega entrega) {
        this.id = entrega.getId();
        this.status = entrega.getStatus();
        this.quantidadePedida = entrega.getQuantidadePedida();
        this.cicloDoPedido = entrega.getCicloDoPedido();
        this.cicloDisponivel = entrega.getCicloDisponivel();
        this.estoqueAntesDoRecebimento = entrega.getEstoqueAntesDoRecebimento();
        this.estoqueDepoisDoRecebimento = entrega.getEstoqueDepoisDoRecebimento();
        this.quantidadeExcedente = entrega.getQuantidadeExcedente();
        this.cicloDoRecebimento = entrega.getCicloDoRecebimento();

        if (entrega.getEstoqueJogo() != null) {
            this.estoqueId = entrega.getEstoqueJogo().getId();
            if (entrega.getEstoqueJogo().getIngrediente() != null) {
                this.nomeIngrediente = entrega.getEstoqueJogo().getIngrediente().getNome();
            }
        }
    }

	public Long getId() {
		return id;
	}

	public Long getEstoqueId() {
		return estoqueId;
	}

	public String getNomeIngrediente() {
		return nomeIngrediente;
	}

	public StatusEntrega getStatus() {
		return status;
	}

	public BigDecimal getQuantidadePedida() {
		return quantidadePedida;
	}

	public int getCicloDoPedido() {
		return cicloDoPedido;
	}

	public int getCicloDisponivel() {
		return cicloDisponivel;
	}

	public BigDecimal getEstoqueAntesDoRecebimento() {
		return estoqueAntesDoRecebimento;
	}

	public BigDecimal getEstoqueDepoisDoRecebimento() {
		return estoqueDepoisDoRecebimento;
	}

	public BigDecimal getQuantidadeExcedente() {
		return quantidadeExcedente;
	}

	public Integer getCicloDoRecebimento() {
		return cicloDoRecebimento;
	}
    
    
    
}