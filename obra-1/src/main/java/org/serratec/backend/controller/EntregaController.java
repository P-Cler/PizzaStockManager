package org.serratec.backend.controller;

import java.util.List;
import org.serratec.backend.DTO.EntregaRequestDTO;
import org.serratec.backend.DTO.EntregaResponseDTO;
import org.serratec.backend.service.EntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/entregas")
public class EntregaController {

    @Autowired
    private EntregaService entregaService;

    @PostMapping
    public ResponseEntity<EntregaResponseDTO> pedirEntrega(@Valid @RequestBody EntregaRequestDTO request) {
        return new ResponseEntity<>(entregaService.pedirEntrega(request), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/receber")
    public ResponseEntity<EntregaResponseDTO> receberEntrega(@PathVariable Long id) {
        return ResponseEntity.ok(entregaService.receberEntrega(id));
    }

    @GetMapping("/por-jogo/{jogoId}")
    public ResponseEntity<List<EntregaResponseDTO>> listarEntregasPorJogo(@PathVariable Long jogoId) {
        return ResponseEntity.ok(entregaService.listarEntregasPorJogo(jogoId));
    }
    
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<EntregaResponseDTO> cancelarEntrega(@PathVariable Long id) {
        return ResponseEntity.ok(entregaService.cancelarEntrega(id));
    }
    
}