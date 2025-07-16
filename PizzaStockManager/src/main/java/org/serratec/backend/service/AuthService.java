package org.serratec.backend.service;

import org.serratec.backend.DTO.LoginRequest;
import org.serratec.backend.DTO.LoginResponse;
import org.serratec.backend.config.JwtUtil;
import org.serratec.backend.entity.Professor;
import org.serratec.backend.entity.Usuario;
import org.serratec.backend.enums.Role;
import org.serratec.backend.exceptions.ResourceNotFoundException;
import org.serratec.backend.repository.AlunoRepository;
import org.serratec.backend.repository.ProfessorRepository;
import org.serratec.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository; 

    @Autowired
    private AlunoRepository alunoRepository; 
    
    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
    	Usuario usuario = usuarioRepository.findByEmail(request.email())
    			.orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado com o email " + request.email()));

        if (!passwordEncoder.matches(request.senha(), usuario.getPassword())) {
            throw new IllegalArgumentException("Credenciais inválidas");
        }

        Boolean primeiroAcesso = false;

        if (usuario.getRole() == Role.ROLE_PROFESSOR) {
            Professor professor = professorRepository.findByUsuario(usuario)
                    .orElseThrow(() -> new ResourceNotFoundException("Perfil de professor não encontrado."));
            
            if (!professor.getAutorizado()) {
                throw new DisabledException("Conta de professor pendente de autorização.");
            }
        } 
        else if (usuario.getRole() == Role.ROLE_ALUNO) {
            primeiroAcesso = alunoRepository.findByUsuario(usuario)
                    .map(aluno -> aluno.getPrimeiroAcesso())
                    .orElse(false);
        }

        String token = jwtUtil.generateToken(usuario);

        return new LoginResponse(token, primeiroAcesso, usuario.getRole().name());
    }
}