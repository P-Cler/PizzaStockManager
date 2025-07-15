package org.serratec.backend.DTO;

import java.util.List;
import jakarta.validation.constraints.NotNull;

public record AssociarTurmasRequestDTO(
    @NotNull
    List<Long> turmaIds
) {}
