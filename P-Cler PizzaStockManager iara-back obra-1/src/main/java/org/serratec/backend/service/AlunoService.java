package org.serratec.backend.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.serratec.backend.dto.AlunoRequest;
import org.serratec.backend.dto.AlunoResponse;
import org.serratec.backend.entity.Aluno;
import org.serratec.backend.entity.Turma;
import org.serratec.backend.exception.DuplicateResourceException;
import org.serratec.backend.exception.ResourceNotFoundException;
import org.serratec.backend.repository.AlunoRepository;
import org.serratec.backend.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlunoService {

	@Autowired
    private AlunoRepository alunoRepository;
	
	@Autowired
    private TurmaRepository turmaRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    private EmailService emailService;
    

    public AlunoResponse criar(AlunoRequest request) {
        try {

            if (alunoRepository.existsByEmail(request.email()) || alunoRepository.existsByCodMatricula(request.codMatricula())) {
                throw new DuplicateResourceException("Email ou matrícula já cadastrado.");
            }

            var aluno = new Aluno();
            aluno.setNome(request.nome());
            aluno.setCodMatricula(request.codMatricula());
            aluno.setEmail(request.email());

            String senhaProvisoria = gerarSenhaProvisoria();
            aluno.setSenhaHash(passwordEncoder.encode(senhaProvisoria));

            aluno.setPrimeiroAcesso(true);

            if (request.turmaId() != null && !request.turmaId().isEmpty()) {
                var turmas = new HashSet<Turma>(turmaRepository.findAllById(request.turmaId()));
                aluno.setTurmas(turmas);
            }

            alunoRepository.save(aluno);

            enviarEmailPrimeiroAcesso(aluno, senhaProvisoria);

            return new AlunoResponse(
                    aluno.getId(),
                    aluno.getNome(),
                    aluno.getCodMatricula(),
                    aluno.getEmail(),
                    aluno.getPrimeiroAcesso()
            );
        } catch (Exception ex) {
            throw ex;
        }
    }

    private String gerarSenhaProvisoria() {
        int tamanho = 8;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder senha = new StringBuilder();
        for (int i = 0; i < tamanho; i++) {
            int index = (int) (Math.random() * chars.length());
            senha.append(chars.charAt(index));
        }
        return senha.toString();
    }

    private void enviarEmailPrimeiroAcesso(Aluno aluno, String senhaProvisoria) {
        Map<String, String> variaveis = Map.of(
                "nome", aluno.getNome(),
                "senha", senhaProvisoria
        );

        emailService.enviarEmailComTemplate(
                aluno.getEmail(),
                "Acesso Inicial - Sistema de Alunos",
                "templates/senha-provisoria-template.html",
                variaveis
        );
    }

    public void deletar(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Aluno com ID " + id + " não encontrado.");
        }

        alunoRepository.deleteById(id);
    }
    
    public AlunoResponse associarTurmas(Long alunoId, List<Long> turmaIds) {
        var aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado"));

        var turmas = turmaRepository.findAllById(turmaIds);
        if (turmas.size() != turmaIds.size()) {
            throw new ResourceNotFoundException("Uma ou mais turmas não foram encontradas.");
        }

        aluno.getTurmas().addAll(turmas);

        alunoRepository.save(aluno);

        return new AlunoResponse(
            aluno.getId(),
            aluno.getNome(),
            aluno.getCodMatricula(),
            aluno.getEmail(),
            aluno.getPrimeiroAcesso()
        );
    }


}
