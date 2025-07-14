package org.serratec.backend.controller;

import org.serratec.backend.DTO.ReceitaRequestDTO;
import org.serratec.backend.DTO.ReceitaResponseDTO;
import org.serratec.backend.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    @PostMapping
    public ResponseEntity<ReceitaResponseDTO> criarReceita(@RequestBody ReceitaRequestDTO request) {
        return new ResponseEntity<>(receitaService.criar(request), HttpStatus.CREATED);
    }
    
    @PostMapping("/completo")
    public ResponseEntity<ReceitaResponseDTO> criarComIngredientesEReceitas(@RequestBody ReceitaRequestDTO request) {
        ReceitaResponseDTO response = receitaService.criarComIngredientesEReceitas(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReceitaResponseDTO>> listarReceitas() {
        return ResponseEntity.ok(receitaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceitaResponseDTO> buscarReceitaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(receitaService.buscarPorId(id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        receitaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}