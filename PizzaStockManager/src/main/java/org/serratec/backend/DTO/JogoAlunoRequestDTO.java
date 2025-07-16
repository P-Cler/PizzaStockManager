package org.serratec.backend.DTO;

import jakarta.validation.constraints.NotNull;

public class JogoAlunoRequestDTO {

    @NotNull(message = "O ID do jogo é obrigatório.")
    private Long jogoId;

    @NotNull(message = "O ID do aluno é obrigatório.")
    private Long alunoId;

    public Long getJogoId() {
        return jogoId;
    }

    public void setJogoId(Long jogoId) {
        this.jogoId = jogoId;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }
}