package org.serratec.backend.service;

import org.serratec.backend.entity.Jogo;
import org.serratec.backend.entity.JogoReceita;
import org.serratec.backend.entity.Receita;
import org.serratec.backend.exceptions.ResourceNotFoundException;
import org.serratec.backend.repository.JogoReceitaRepository;
import org.serratec.backend.repository.JogoRepository;
import org.serratec.backend.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JogoReceitaService {
    @Autowired private JogoRepository jogoRepository;
    @Autowired private ReceitaRepository receitaRepository;
    @Autowired private JogoReceitaRepository jogoReceitaRepository;

    public String associar(Long jogoId, Long receitaId) {
        Jogo jogo = jogoRepository.findById(jogoId).orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado"));
        Receita receita = receitaRepository.findById(receitaId).orElseThrow(() -> new ResourceNotFoundException("Receita não encontrada"));

        if (jogoReceitaRepository.existsByJogoIdAndReceitaId(jogoId, receitaId)) {
            return "Associação já existe.";
        }
        
        JogoReceita jogoReceita = new JogoReceita();
        jogoReceita.setJogo(jogo);
        jogoReceita.setReceita(receita);
        jogoReceitaRepository.save(jogoReceita);
        return "Receita '" + receita.getNome() + "' associada ao jogo com ID " + jogo.getId() + " com sucesso.";
    }
}