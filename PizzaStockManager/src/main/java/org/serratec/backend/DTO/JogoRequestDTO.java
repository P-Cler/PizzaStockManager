package org.serratec.backend.DTO;

public class JogoRequestDTO {

    private int tempoTotal;
    private int numeroCiclos;
    private int maximoPizzas;
    private int minimoPizzas;
    private int pontosPorPizzaProduzida;
    private int pontosPerdidosPorPizzaRejeitada;
    private int pontosPerdidosPorExcedenteEstoque;
    private int pontosPerdidosPorFaltaEstoque;
    private int pontosPerdidosPorEstoquePerdido;
    private int totalPontosPerdidosPorEstoquePerdido;

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

	public int getTotalPontosPerdidosPorEstoquePerdido() {
		return totalPontosPerdidosPorEstoquePerdido;
	}

	public void setTotalPontosPerdidosPorEstoquePerdido(int totalPontosPerdidosPorEstoquePerdido) {
		this.totalPontosPerdidosPorEstoquePerdido = totalPontosPerdidosPorEstoquePerdido;
	}
    
    
    
}