package org.serratec.backend.controller;

import org.serratec.backend.service.JogoReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jogos-receitas")
public class JogoReceitaController {

    @Autowired
    private JogoReceitaService jogoReceitaService;

    @PostMapping("/jogo/{jogoId}/receita/{receitaId}")
    public ResponseEntity<String> associarReceitaAoJogo(@PathVariable Long jogoId, @PathVariable Long receitaId) {
        String mensagem = jogoReceitaService.associar(jogoId, receitaId);
        return ResponseEntity.ok(mensagem);
    }
}