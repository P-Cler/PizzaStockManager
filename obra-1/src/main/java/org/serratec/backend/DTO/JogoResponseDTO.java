package org.serratec.backend.DTO;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.serratec.backend.entity.Jogo;
import org.serratec.backend.enums.JogoStatus;

public class JogoResponseDTO {

    private Long id;
    private int tempoTotal;
    private int numeroCiclos;
    private int maximoPizzas;
    private int duracaoCiclos;
    private int minimoPizzas;
    private JogoStatus status;
    private int cicloAtual;
    private long tempoRestanteCicloSegundos;
    private long tempoRestanteTotalSegundos; 

    private List<PedidoResponseDTO> pedidos;
    
    public JogoResponseDTO(Jogo jogo) {
        this.id = jogo.getId();
        this.tempoTotal = jogo.getTempoTotal();
        this.numeroCiclos = jogo.getNumeroCiclos();
        this.maximoPizzas = jogo.getMaximoPizzas();
        this.minimoPizzas = jogo.getMinimoPizzas();
        this.duracaoCiclos = jogo.getDuracaoCiclos();
        this.status = jogo.getStatus();
        
        if (jogo.getPedidos() != null) {
            this.pedidos = jogo.getPedidos().stream()
                               .map(PedidoResponseDTO::new)
                               .collect(Collectors.toList());
        } else {
            this.pedidos = Collections.emptyList();
        }

        
        if (jogo.getStatus() == JogoStatus.EM_ANDAMENTO || jogo.getStatus() == JogoStatus.PAUSADO) {
            long duracaoCicloEmSegundos = (long) jogo.getTempoTotal() * 60 / jogo.getNumeroCiclos();
            
            if (duracaoCicloEmSegundos > 0) {
            	LocalDateTime momentoReferencia = (jogo.getStatus() == JogoStatus.PAUSADO) 
                        ? jogo.getDataPausa() 
                        : LocalDateTime.now();
                
                long segundosCorridos = Duration.between(jogo.getDataInicio(), momentoReferencia).getSeconds();
                
                this.cicloAtual = (int) (segundosCorridos / duracaoCicloEmSegundos) + 1;
                
                long tempoDecorridoNoCicloAtual = segundosCorridos % duracaoCicloEmSegundos;
                this.tempoRestanteCicloSegundos = duracaoCicloEmSegundos - tempoDecorridoNoCicloAtual;
                long tempoTotalEmSegundos = (long) jogo.getTempoTotal() * 60;
                
                long segundosCorridosTotal = Duration.between(jogo.getDataInicio(), momentoReferencia).getSeconds();
                this.tempoRestanteTotalSegundos = tempoTotalEmSegundos - segundosCorridosTotal;
                if (this.cicloAtual > jogo.getNumeroCiclos()) {
                    this.cicloAtual = jogo.getNumeroCiclos();
                    this.tempoRestanteCicloSegundos = 0;
                }
            }
        } else {
            this.cicloAtual = 0;
            this.tempoRestanteCicloSegundos = 0;
            this.tempoRestanteTotalSegundos = 0;
            if (this.status == JogoStatus.FINALIZADO) {
                 this.cicloAtual = jogo.getNumeroCiclos();
            }
        }
    }
        
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTempoTotal() {
        return tempoTotal;
    }

    public void setTempoTotal(int tempoTotal) {
        this.tempoTotal = tempoTotal;
    }

    public int getNumeroCiclos() {
        return numeroCiclos;
    }

    public void setNumeroCiclos(int numeroCiclos) {
        this.numeroCiclos = numeroCiclos;
    }

    public int getMaximoPizzas() {
        return maximoPizzas;
    }

    public void setMaximoPizzas(int maximoPizzas) {
        this.maximoPizzas = maximoPizzas;
    }

	public int getDuracaoCiclos() {
		return duracaoCiclos;
	}

	public void setDuracaoCiclos(int duracaoCiclos) {
		this.duracaoCiclos = duracaoCiclos;
	}

	public int getMinimoPizzas() {
		return minimoPizzas;
	}

	public void setMinimoPizzas(int minimoPizzas) {
		this.minimoPizzas = minimoPizzas;
	}

	public JogoStatus getStatus() {
		return status;
	}

	public void setStatus(JogoStatus status) {
		this.status = status;
	}

	public int getCicloAtual() {
		return cicloAtual;
	}

	public void setCicloAtual(int cicloAtual) {
		this.cicloAtual = cicloAtual;
	}

	public long getTempoRestanteCicloSegundos() {
		return tempoRestanteCicloSegundos;
	}

	public void setTempoRestanteCicloSegundos(long tempoRestanteCicloSegundos) {
		this.tempoRestanteCicloSegundos = tempoRestanteCicloSegundos;
	}

	public List<PedidoResponseDTO> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<PedidoResponseDTO> pedidos) {
		this.pedidos = pedidos;
	}
    
	public long getTempoRestanteTotalSegundos() {
        return tempoRestanteTotalSegundos;
    }

    public void setTempoRestanteTotalSegundos(long tempoRestanteTotalSegundos) {
        this.tempoRestanteTotalSegundos = tempoRestanteTotalSegundos;
    }
    
}