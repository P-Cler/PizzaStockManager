package org.serratec.backend.DTO;

import java.time.LocalDateTime;

import org.serratec.backend.entity.Pedido;
import org.serratec.backend.enums.StatusPedido;

public class PedidoResponseDTO {
    private Long id;
    private String codigoPedido;
    private String nomeReceita;
    private LocalDateTime dataPedido;
    private int quantidadePizzas;
    private StatusPedido status;
    private Long jogoId;

    public PedidoResponseDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.codigoPedido = pedido.getCodigoPedido();
        this.nomeReceita = pedido.getNomeReceita();
        this.dataPedido = pedido.getDataPedido();
        this.quantidadePizzas = pedido.getQuantidadePizzas();
        this.status = pedido.getStatus();
        this.jogoId = pedido.getJogo().getId();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoPedido() {
		return codigoPedido;
	}

	public void setCodigoPedido(String codigoPedido) {
		this.codigoPedido = codigoPedido;
	}

	public String getNomeReceita() {
		return nomeReceita;
	}

	public void setNomeReceita(String nomeReceita) {
		this.nomeReceita = nomeReceita;
	}

	public LocalDateTime getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDateTime dataPedido) {
		this.dataPedido = dataPedido;
	}

	public int getQuantidadePizzas() {
		return quantidadePizzas;
	}

	public void setQuantidadePizzas(int quantidadePizzas) {
		this.quantidadePizzas = quantidadePizzas;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	public Long getJogoId() {
		return jogoId;
	}

	public void setJogoId(Long jogoId) {
		this.jogoId = jogoId;
	}
    
    
    
}