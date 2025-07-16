package org.serratec.backend.controller;

import org.serratec.backend.DTO.ResultadoJogoDTO;
import org.serratec.backend.service.ResultadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resultados")
public class ResultadoJogoController {

    @Autowired
    private ResultadoService resultadoService;

    @GetMapping("/jogo/{jogoId}")
    public ResponseEntity<ResultadoJogoDTO> buscarResultadoPorJogo(@PathVariable Long jogoId) {
        return ResponseEntity.ok(resultadoService.buscarPorJogoId(jogoId));
    }
}