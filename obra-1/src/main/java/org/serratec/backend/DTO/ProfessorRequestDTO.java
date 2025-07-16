package org.serratec.backend.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ProfessorRequestDTO(
    @NotBlank(message = "O nome não pode ser vazio") String nome,
    @NotBlank(message = "O email não pode ser vazio") @Email String email
) {}