package org.serratec.backend.DTO;

import java.math.BigDecimal;

public class EstoqueRequestDTO {

    private Long jogoId;
    private Long ingredienteId;
    private BigDecimal estoqueAtual;
    private BigDecimal estoqueMinimo;
    private BigDecimal estoqueMax;
    private int leadTime;
    private BigDecimal pontoPedido;

    public Long getJogoId() {
        return jogoId;
    }

    public void setJogoId(Long jogoId) {
        this.jogoId = jogoId;
    }

    public Long getIngredienteId() {
        return ingredienteId;
    }

    public void setIngredienteId(Long ingredienteId) {
        this.ingredienteId = ingredienteId;
    }

    public BigDecimal getEstoqueAtual() {
        return estoqueAtual;
    }

    public void setEstoqueAtual(BigDecimal estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
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
}