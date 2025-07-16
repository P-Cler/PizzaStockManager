package org.serratec.backend.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.serratec.backend.enums.JogoStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;

@Entity
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1, message = "O tempo total deve ser maior que zero.")
    private int tempoTotal;

    @Min(value = 1, message = "O número de ciclos deve ser maior que zero.")
    private int numeroCiclos;

    @Min(value = 1, message = "O número máximo de pizzas deve ser maior que zero.")
    private int maximoPizzas;
    
    @Min(value = 1, message = "O número máximo de pizzas deve ser maior que zero.")
    private int minimoPizzas;

    @Transient
    private int duracaoCiclos; 

    @OneToMany(mappedBy = "jogo", cascade = CascadeType.ALL)
    private List<JogoReceita> receitas;

    @OneToMany(mappedBy = "jogo", cascade = CascadeType.ALL)
    private List<JogoAluno> alunos;

    @OneToMany(mappedBy = "jogo", cascade = CascadeType.ALL)
    private List<EstoqueJogo> estoque;
    
    @OneToMany(mappedBy = "jogo", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;
    
    @Enumerated(EnumType.STRING)
    private JogoStatus status;
    
    private LocalDateTime dataInicio;
    
    private LocalDateTime dataPausa;
    
    private int pontosPorPizzaProduzida;
    
    private int pontosPerdidosPorPizzaRejeitada;
    
    private int pontosPerdidosPorExcedenteEstoque; 
    
    private int pontosPerdidosPorFaltaEstoque;
    
    private int pontosPerdidosPorEstoquePerdido;
    
    private int totalCiclosComEstoqueBaixo;

    @PostLoad
    @PostPersist
    @PostUpdate
    public void calcularDuracaoCiclos() {
        if (tempoTotal > 0 && numeroCiclos > 0) {
            this.duracaoCiclos = tempoTotal / numeroCiclos;
        }
    }
    
    public int calcularCicloAtual() {
        if (this.status == null || this.status == JogoStatus.NAO_INICIADO) {
            return 0;
        }

        if (this.status == JogoStatus.FINALIZADO) {
            return this.numeroCiclos;
        }

        if (this.dataInicio == null) {
            return 0;
        }

        LocalDateTime momentoReferencia = (this.status == JogoStatus.PAUSADO && this.dataPausa != null)
                                          ? this.dataPausa
                                          : LocalDateTime.now();

        if (this.numeroCiclos == 0 || this.tempoTotal == 0) {
            return 1; 
        }

        long duracaoCicloEmSegundos = (long) this.tempoTotal * 60 / this.numeroCiclos;

        if (duracaoCicloEmSegundos <= 0) {
            return 1;
        }

        long segundosCorridos = Duration.between(this.dataInicio, momentoReferencia).getSeconds();
        int cicloCalculado = (int) (segundosCorridos / duracaoCicloEmSegundos) + 1;

        return Math.min(cicloCalculado, this.numeroCiclos);
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

	public int getMinimoPizzas() {
		return minimoPizzas;
	}

	public void setMinimoPizzas(int minimoPizzas) {
		this.minimoPizzas = minimoPizzas;
	}

	public int getDuracaoCiclos() {
		return duracaoCiclos;
	}

	public void setDuracaoCiclos(int duracaoCiclos) {
		this.duracaoCiclos = duracaoCiclos;
	}

	public List<JogoReceita> getReceitas() {
		return receitas;
	}

	public void setReceitas(List<JogoReceita> receitas) {
		this.receitas = receitas;
	}

	public List<JogoAluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<JogoAluno> alunos) {
		this.alunos = alunos;
	}

	public List<EstoqueJogo> getEstoque() {
		return estoque;
	}

	public void setEstoque(List<EstoqueJogo> estoque) {
		this.estoque = estoque;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public JogoStatus getStatus() {
		return status;
	}

	public void setStatus(JogoStatus status) {
		this.status = status;
	}

	public LocalDateTime getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDateTime getDataPausa() {
		return dataPausa;
	}

	public void setDataPausa(LocalDateTime dataPausa) {
		this.dataPausa = dataPausa;
	}

	public int getPontosPorPizzaProduzida() {
		return pontosPorPizzaProduzida;
	}

	public void setPontosPorPizzaProduzida(int pontosPorPizzaProduzida) {
		this.pontosPorPizzaProduzida = pontosPorPizzaProduzida;
	}

	public int getPontosPerdidosPorPizzaRejeitada() {
		return pontosPerdidosPorPizzaRejeitada;
	}

	public void setPontosPerdidosPorPizzaRejeitada(int pontosPerdidosPorPizzaRejeitada) {
		this.pontosPerdidosPorPizzaRejeitada = pontosPerdidosPorPizzaRejeitada;
	}

	public int getPontosPerdidosPorExcedenteEstoque() {
		return pontosPerdidosPorExcedenteEstoque;
	}

	public void setPontosPerdidosPorExcedenteEstoque(int pontosPerdidosPorExcedenteEstoque) {
		this.pontosPerdidosPorExcedenteEstoque = pontosPerdidosPorExcedenteEstoque;
	}

	public int getPontosPerdidosPorFaltaEstoque() {
		return pontosPerdidosPorFaltaEstoque;
	}

	public void setPontosPerdidosPorFaltaEstoque(int pontosPerdidosPorFaltaEstoque) {
		this.pontosPerdidosPorFaltaEstoque = pontosPerdidosPorFaltaEstoque;
	}

	public int getPontosPerdidosPorEstoquePerdido() {
		return pontosPerdidosPorEstoquePerdido;
	}

	public void setPontosPerdidosPorEstoquePerdido(int pontosPerdidosPorEstoquePerdido) {
		this.pontosPerdidosPorEstoquePerdido = pontosPerdidosPorEstoquePerdido;
	}

	public int getTotalCiclosComEstoqueBaixo() {
		return totalCiclosComEstoqueBaixo;
	}

	public void setTotalCiclosComEstoqueBaixo(int totalCiclosComEstoqueBaixo) {
		this.totalCiclosComEstoqueBaixo = totalCiclosComEstoqueBaixo;
	}

    
    
	
    
}
