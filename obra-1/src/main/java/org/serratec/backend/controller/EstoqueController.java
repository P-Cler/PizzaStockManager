package org.serratec.backend.controller;

import java.util.List;

import org.serratec.backend.DTO.CampoUpdateRequestDTO;
import org.serratec.backend.DTO.EstoqueJogoResponseDTO;
// Removido o import não utilizado de EstoqueReceitaRequestDTO
import org.serratec.backend.DTO.EstoqueRequestDTO;
import org.serratec.backend.DTO.EstoqueResponseDTO;
import org.serratec.backend.DTO.EstoqueUpdateRequestDTO;
import org.serratec.backend.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/estoques")
public class EstoqueController {

    @Autowired
    private EstoqueService service;

    @PostMapping
    public ResponseEntity<EstoqueResponseDTO> criarEstoque(@RequestBody @Valid EstoqueRequestDTO requestDTO) {
        EstoqueResponseDTO responseDTO = service.criarEstoque(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
    
    @GetMapping("/por-jogo/{jogoId}")
    public ResponseEntity<EstoqueJogoResponseDTO> buscarEstoquePorJogoId(@PathVariable Long jogoId){
        EstoqueJogoResponseDTO estoques = service.listarPorJogoId(jogoId);
        return ResponseEntity.ok(estoques);
    }
    
    @PostMapping("/jogo/{jogoId}/receita/{receitaId}")
    public ResponseEntity<List<EstoqueResponseDTO>> criarEstoquePorReceita(
            @Valid 
            @PathVariable Long jogoId,
            @PathVariable Long receitaId) {
        
        List<EstoqueResponseDTO> estoques = service.adicionarEstoquePorReceita(jogoId, receitaId);
        
        if (estoques.isEmpty()) {
            return ResponseEntity.ok()
                    .header("X-Info-Message", "Nenhum novo estoque foi criado. Itens ja podem existir.")
                    .body(estoques);
        }
        
        return new ResponseEntity<>(estoques, HttpStatus.CREATED);
    }
    
    @PutMapping("/{idEstoque}")
    public ResponseEntity<EstoqueResponseDTO> atualizarEstoque(
            @Valid
    		@PathVariable Long idEstoque, 
            @RequestBody EstoqueUpdateRequestDTO updateDTO) {
        
        EstoqueResponseDTO estoqueAtualizado = service.atualizarEstoque(idEstoque, updateDTO);
        return ResponseEntity.ok(estoqueAtualizado);
    }
    
    @PatchMapping("/jogo/{jogoId}/ingrediente/{ingredienteId}/campo/{campoId}")
    public ResponseEntity<EstoqueResponseDTO> atualizarCampoEspecifico(
            @Valid
    		@PathVariable Long jogoId,
            @PathVariable Long ingredienteId,
            @PathVariable int campoId,
            @RequestBody CampoUpdateRequestDTO valorDTO) {
        
        EstoqueResponseDTO estoqueAtualizado = service.atualizarCampoEspecifico(jogoId, ingredienteId, campoId, valorDTO.getValor());
        return ResponseEntity.ok(estoqueAtualizado);
    }
}