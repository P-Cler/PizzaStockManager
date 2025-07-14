package org.serratec.backend.DTO;

public class JogoRequestDTO {

    private int tempoTotal;
    private int numeroCiclos;
    private int maximoPizzas;
    private int minimoPizzas;

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
    
    
    
}