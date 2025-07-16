package org.serratec.backend.service;

import org.serratec.backend.DTO.ProfessorRequestDTO;
import org.serratec.backend.DTO.ProfessorResponseDTO;
import org.serratec.backend.entity.Professor;
import org.serratec.backend.entity.Usuario;
import org.serratec.backend.enums.Role;
import org.serratec.backend.exceptions.DuplicateResourceException;
import org.serratec.backend.repository.ProfessorRepository;
import org.serratec.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    

    @Transactional
    public ProfessorResponseDTO criar(ProfessorRequestDTO request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email já cadastrado.");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(request.email());
        novoUsuario.setRole(Role.ROLE_PROFESSOR); 
        novoUsuario.setSenha(passwordEncoder.encode("senhaPadrao123")); 

        Professor professor = new Professor();
        professor.setNome(request.nome());
        professor.setUsuario(novoUsuario); 

        professorRepository.save(professor);


        return new ProfessorResponseDTO(
            professor.getId(),
            professor.getNome(),
            professor.getUsuario().getEmail(),
            professor.getUsuario().getRole().name()
        );
    }
}