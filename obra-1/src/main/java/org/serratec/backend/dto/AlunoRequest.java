package org.serratec.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AlunoRequest(
        @NotBlank
        String nome,
        @NotBlank
        String codMatricula,
        @Email
        @NotBlank
        String email,
        @NotNull
        Long turmaId) {

}
