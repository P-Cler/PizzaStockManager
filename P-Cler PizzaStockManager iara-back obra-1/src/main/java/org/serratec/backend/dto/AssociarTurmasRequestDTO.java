package org.serratec.backend.dto;

import java.util.List;
import jakarta.validation.constraints.NotNull;

public record AssociarTurmasRequestDTO(
    @NotNull
    List<Long> turmaIds
) {}
