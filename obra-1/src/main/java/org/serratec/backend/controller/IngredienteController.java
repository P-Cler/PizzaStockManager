package org.serratec.backend.controller;

import java.util.List;
import org.serratec.backend.DTO.IngredienteRequestDTO;
import org.serratec.backend.DTO.IngredienteResponseDTO;
import org.serratec.backend.service.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;

    @PostMapping
    public ResponseEntity<IngredienteResponseDTO> criar(@RequestBody IngredienteRequestDTO request) {
        return new ResponseEntity<>(ingredienteService.criar(request), HttpStatus.CREATED);
    }
    
    @PostMapping("/varios")
    public ResponseEntity<List<IngredienteResponseDTO>> criarVarios(@RequestBody List<IngredienteRequestDTO> requests) {
        List<IngredienteResponseDTO> criados = ingredienteService.criarVarios(requests);
        return new ResponseEntity<>(criados, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<IngredienteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(ingredienteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredienteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ingredienteService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredienteResponseDTO> atualizar(@PathVariable Long id, @RequestBody IngredienteRequestDTO request) {
        return ResponseEntity.ok(ingredienteService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        ingredienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}