package org.serratec.backend.service;

import lombok.RequiredArgsConstructor;
import org.serratec.backend.config.JwtUtil;
import org.serratec.backend.dto.LoginRequest;
import org.serratec.backend.dto.LoginResponse;
import org.serratec.backend.exception.ResourceNotFoundException;
import org.serratec.backend.entity.Aluno;
import org.serratec.backend.repository.AlunoRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AlunoRepository alunoRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        Aluno aluno = alunoRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado"));

        if (!passwordEncoder.matches(request.senha(), aluno.getSenhaHash())) {
            throw new IllegalArgumentException("Credenciais inválidas");
        }

        String token = jwtUtil.generateToken(aluno.getEmail());

        return new LoginResponse(token, aluno.getPrimeiroAcesso());
    }
}
