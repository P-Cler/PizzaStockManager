package org.serratec.backend.controller;

import java.util.List;

import org.serratec.backend.dto.AlunoResponse;
import org.serratec.backend.dto.AssociarTurmasRequestDTO;
import org.serratec.backend.service.AlunoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PutMapping("/{id}/turmas")
    public ResponseEntity<AlunoResponse> associarTurmas(
            @PathVariable Long id,
            @RequestBody AssociarTurmasRequestDTO request) {
        var response = alunoService.associarTurmas(id, request.turmaIds());
        return ResponseEntity.ok(response);
    }

}
