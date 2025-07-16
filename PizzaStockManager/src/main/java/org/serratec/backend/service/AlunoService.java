package org.serratec.backend.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.serratec.backend.DTO.AlunoRequest;
import org.serratec.backend.DTO.AlunoRequestDTO;
import org.serratec.backend.DTO.AlunoResponse;
import org.serratec.backend.DTO.AlunoResponseDTO;
import org.serratec.backend.entity.Aluno;
import org.serratec.backend.entity.Usuario;
import org.serratec.backend.enums.Role;
import org.serratec.backend.exceptions.DuplicateResourceException;
import org.serratec.backend.exceptions.ResourceNotFoundException;
import org.serratec.backend.repository.AlunoRepository;
import org.serratec.backend.repository.TurmaRepository;
import org.serratec.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;
	
	@Autowired
    private TurmaRepository turmaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    private EmailService emailService;
    

    
    public List<AlunoResponseDTO> listarTodos() {
        return alunoRepository.findAll().stream()
                .map(AlunoResponseDTO::new)
                .collect(Collectors.toList());
    }

    
    public AlunoResponseDTO buscarPorId(Long id) {
        return alunoRepository.findById(id)
                .map(AlunoResponseDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com o ID: " + id));
    }

    
    public AlunoResponseDTO atualizar(Long id, AlunoRequestDTO requestDTO) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com o ID: " + id));
        
        aluno.setNome(requestDTO.getNome());
        alunoRepository.save(aluno);
        
        return new AlunoResponseDTO(aluno);
    }
    

    

    @Transactional
    public AlunoResponse criar(AlunoRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email já cadastrado.");
        }
        if (alunoRepository.existsByCodMatricula(request.codMatricula())) {
            throw new DuplicateResourceException("Matrícula já cadastrada.");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(request.email());
        novoUsuario.setRole(Role.ROLE_ALUNO); 
        String senhaProvisoria = gerarSenhaProvisoria();
        novoUsuario.setSenha(passwordEncoder.encode(senhaProvisoria));

        // 2. Criar a entidade Aluno
        Aluno aluno = new Aluno();
        aluno.setNome(request.nome());
        aluno.setCodMatricula(request.codMatricula());
        aluno.setPrimeiroAcesso(true);
        aluno.setUsuario(novoUsuario); 

        if (request.turmaId() != null && !request.turmaId().isEmpty()) {
            var turmas = new HashSet<>(turmaRepository.findAllById(request.turmaId()));
            aluno.setTurmas(turmas);
        }

        alunoRepository.save(aluno);

        enviarEmailPrimeiroAcesso(aluno, senhaProvisoria);

        return new AlunoResponse(
                aluno.getId(),
                aluno.getNome(),
                aluno.getCodMatricula(),
                aluno.getUsuario().getEmail(), 
                aluno.getPrimeiroAcesso()
        );
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
                aluno.getUsuario().getEmail(),
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
            aluno.getUsuario().getEmail(),
            aluno.getPrimeiroAcesso()
        );
    }
    
}