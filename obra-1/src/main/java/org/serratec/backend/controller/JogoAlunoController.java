package org.serratec.backend.controller;

import java.util.List;

import org.serratec.backend.DTO.AlunoResponseDTO;
import org.serratec.backend.DTO.JogoAlunoRequestDTO;
import org.serratec.backend.service.JogoAlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/jogos-alunos")
public class JogoAlunoController {

    @Autowired
    private JogoAlunoService jogoAlunoService;

    @PostMapping
    public ResponseEntity<String> associar(@Valid @RequestBody JogoAlunoRequestDTO request) {
        String mensagem = jogoAlunoService.associar(request);
        return ResponseEntity.ok(mensagem);
    }

    @DeleteMapping("/jogo/{jogoId}/aluno/{alunoId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long jogoId, @PathVariable Long alunoId) {
        jogoAlunoService.desassociar(jogoId, alunoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/jogo/{jogoId}")
    public ResponseEntity<List<AlunoResponseDTO>> listarAlunosPorJogo(@PathVariable Long jogoId) {
        return ResponseEntity.ok(jogoAlunoService.listarAlunosPorJogo(jogoId));
    }
}