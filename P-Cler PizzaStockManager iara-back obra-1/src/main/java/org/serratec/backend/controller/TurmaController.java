package org.serratec.backend.controller;

import java.util.List;

import org.serratec.backend.dto.AlunoResponse;
import org.serratec.backend.dto.AssociarTurmasRequestDTO;
import org.serratec.backend.dto.TurmaRequest;
import org.serratec.backend.dto.TurmaResponse;
import org.serratec.backend.service.AlunoService;
import org.serratec.backend.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/turmas")
@RequiredArgsConstructor
public class TurmaController {

	@Autowired
    private TurmaService turmaService;

    @PostMapping
    public ResponseEntity<TurmaResponse> adicionar(@RequestBody TurmaRequest request) {
        return ResponseEntity.ok(turmaService.adicionar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaResponse> atualizar(@PathVariable Long id, @RequestBody TurmaRequest request) {
        return ResponseEntity.ok(turmaService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        turmaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TurmaResponse>> listar() {
        return ResponseEntity.ok(turmaService.listar());
    }
   
    
}
