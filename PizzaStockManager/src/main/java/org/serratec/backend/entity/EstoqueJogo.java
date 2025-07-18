package org.serratec.backend.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class EstoqueJogo {

	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jogo_id")
    @NotNull(message = "O jogo é obrigatório")
    private Jogo jogo;

    @ManyToOne
    @JoinColumn(name = "ingrediente_id")
    @NotNull(message = "O ingrediente é obrigatório")
    private Ingrediente ingrediente;

    @NotNull(message = "O estoque mínimo é obrigatório")
    @DecimalMin(value = "0.0", inclusive = true, message = "O estoque mínimo não pode ser negativo")
    private BigDecimal estoqueMinimo;

    @NotNull(message = "O estoque máximo é obrigatório")
    @DecimalMin(value = "0.0", inclusive = true, message = "O estoque máximo não pode ser negativo")
    private BigDecimal estoqueMax;

    @Min(value = 0, message = "O lead time não pode ser negativo")
    private int leadTime;

    @NotNull(message = "O ponto de pedido é obrigatório")
    @DecimalMin(value = "0.0", inclusive = true, message = "O ponto de pedido não pode ser negativo")
    private BigDecimal pontoPedido;
    
    private String unidade;
    
    @OneToMany(mappedBy = "estoqueJogo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<LoteEstoque> lotes = new ArrayList<>();
    
    private Integer validadeEmCiclos;

    
    @Transient
    public BigDecimal getEstoqueAtual() {
    	if(this.lotes == null) {
    		return BigDecimal.ZERO;
    	}
    	return this.lotes.stream()
    			.map(LoteEstoque::getQuantidade)
    			.reduce(BigDecimal.ZERO, BigDecimal::add);
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}

	public BigDecimal getEstoqueMinimo() {
		return estoqueMinimo;
	}

	public void setEstoqueMinimo(BigDecimal estoqueMinimo) {
		this.estoqueMinimo = estoqueMinimo;
	}

	public BigDecimal getEstoqueMax() {
		return estoqueMax;
	}

	public void setEstoqueMax(BigDecimal estoqueMax) {
		this.estoqueMax = estoqueMax;
	}

	public int getLeadTime() {
		return leadTime;
	}

	public void setLeadTime(int leadTime) {
		this.leadTime = leadTime;
	}

	public BigDecimal getPontoPedido() {
		return pontoPedido;
	}

	public void setPontoPedido(BigDecimal pontoPedido) {
		this.pontoPedido = pontoPedido;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public List<LoteEstoque> getLotes() {
		return lotes;
	}

	public void setLotes(List<LoteEstoque> lotes) {
		this.lotes = lotes;
	}
    
	public Integer getValidadeEmCiclos() {
		return validadeEmCiclos;
	}

	public void setValidadeEmCiclos(Integer validadeEmCiclos) {
		this.validadeEmCiclos = validadeEmCiclos;
	}
    
}
