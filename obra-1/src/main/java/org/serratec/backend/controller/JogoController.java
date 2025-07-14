package org.serratec.backend.controller;

import java.util.List;

import org.serratec.backend.DTO.CampoUpdateRequestDTO;
import org.serratec.backend.DTO.JogoRequestDTO;
import org.serratec.backend.DTO.JogoResponseDTO;
import org.serratec.backend.entity.Jogo;
import org.serratec.backend.service.GameLogicService;
import org.serratec.backend.service.JogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/jogos")
public class JogoController {

    @Autowired
    private JogoService jogoService;
    
    @Autowired
    private GameLogicService gameLogicService;

    @PostMapping
    public ResponseEntity<JogoResponseDTO> criarJogo(@RequestBody @Valid JogoRequestDTO requestDTO) {
        JogoResponseDTO responseDTO = jogoService.criarJogo(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JogoResponseDTO> buscarJogoPorId(@PathVariable Long id) {
        JogoResponseDTO responseDTO = jogoService.buscarJogoPorId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<JogoResponseDTO>> listarTodosJogos() {
        List<JogoResponseDTO> jogos = jogoService.listarTodosJogos();
        return ResponseEntity.ok(jogos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarJogo(@PathVariable Long id) {
        jogoService.deletarJogo(id);
        return ResponseEntity.noContent().build(); 
    }
    
    @PostMapping("/{id}/iniciar")
    public ResponseEntity<JogoResponseDTO> iniciarJogo(@PathVariable Long id) {
        Jogo jogoIniciado = gameLogicService.iniciarJogo(id);
        return ResponseEntity.ok(new JogoResponseDTO(jogoIniciado));
    }
    
    @PostMapping("/{id}/pausar")
    public ResponseEntity<JogoResponseDTO> pausarJogo(@PathVariable Long id) {
        Jogo jogoPausado = gameLogicService.pausarJogo(id);
        return ResponseEntity.ok(new JogoResponseDTO(jogoPausado));
    }

    @PostMapping("/{id}/retomar")
    public ResponseEntity<JogoResponseDTO> retomarJogo(@PathVariable Long id) {
        Jogo jogoRetomado = gameLogicService.retomarJogo(id);
        return ResponseEntity.ok(new JogoResponseDTO(jogoRetomado));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<JogoResponseDTO> atualizarJogo(@PathVariable Long id, @RequestBody @Valid JogoRequestDTO requestDTO) {
        JogoResponseDTO response = jogoService.atualizarJogoCompleto(id, requestDTO);
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/{jogoId}/campo/{campoId}")
    public ResponseEntity<JogoResponseDTO> atualizarCampoJogo(
            @PathVariable Long jogoId,
            @PathVariable int campoId,
            @RequestBody CampoUpdateRequestDTO valorDTO) {
        
        JogoResponseDTO jogoAtualizado = jogoService.atualizarCampoJogo(jogoId, campoId, valorDTO.getValor());
        return ResponseEntity.ok(jogoAtualizado);
    }
  
    
}