package org.serratec.backend.DTO;

import org.serratec.backend.entity.Aluno;

public class AlunoResponseDTO {

    private Long id;
    private String nome;

    public AlunoResponseDTO(Aluno aluno) {
        this.id = aluno.getId();
        this.nome = aluno.getNome();
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
}