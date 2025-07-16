package org.serratec.backend.controller;

import java.util.List;

import org.serratec.backend.DTO.PedidoResponseDTO;
import org.serratec.backend.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PatchMapping("/{id}/produzir")
    public ResponseEntity<PedidoResponseDTO> produzir(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.produzirPedido(id));
    }

    @PatchMapping("/{id}/rejeitar")
    public ResponseEntity<PedidoResponseDTO> rejeitar(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.rejeitarPedido(id));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @GetMapping("/por-jogo/{jogoId}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorJogo(@PathVariable Long jogoId) {
        return ResponseEntity.ok(pedidoService.listarPorJogo(jogoId));
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    
}