package org.serratec.backend.controller;

import java.util.List;

import org.serratec.backend.DTO.AlunoRequest;
import org.serratec.backend.DTO.AlunoResponse;
import org.serratec.backend.DTO.AlunoResponseDTO;
import org.serratec.backend.DTO.AssociarTurmasRequestDTO;
import org.serratec.backend.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

	@Autowired
    private AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }
    
    @GetMapping
    public List<AlunoResponseDTO> listarTodosAlunos() {
        return alunoService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<AlunoResponse> criar(@RequestBody AlunoRequest request) {
        AlunoResponse response = alunoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        alunoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/turmas")
    public ResponseEntity<AlunoResponse> associarTurmas(
            @PathVariable Long id,
            @RequestBody AssociarTurmasRequestDTO request) {
        var response = alunoService.associarTurmas(id, request.turmaIds());
        return ResponseEntity.ok(response);
    }
}