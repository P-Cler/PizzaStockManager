package org.serratec.backend.DTO;

import org.serratec.backend.entity.Ingrediente;

public class IngredienteResponseDTO {

    private Long id;
    private String nome;
    private String unidade;

    public IngredienteResponseDTO(Ingrediente ingrediente) {
        this.id = ingrediente.getId();
        this.nome = ingrediente.getNome();
        this.unidade = ingrediente.getUnidade();
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

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }
}