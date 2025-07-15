package org.serratec.backend.service;


import org.serratec.backend.DTO.LoginRequest;
import org.serratec.backend.DTO.LoginResponse;
import org.serratec.backend.config.JwtUtil;
import org.serratec.backend.entity.Aluno;
import org.serratec.backend.exceptions.ResourceNotFoundException;
import org.serratec.backend.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	@Autowired
    private AlunoRepository alunoRepository;
	@Autowired
    private JwtUtil jwtUtil;
	@Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        Aluno aluno = alunoRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com o email: " + request.email()));

        if (!passwordEncoder.matches(request.senha(), aluno.getSenhaHash())) {
            throw new IllegalArgumentException("Credenciais inválidas");
        }

        String token = jwtUtil.generateToken(aluno.getEmail());

        return new LoginResponse(token, aluno.getPrimeiroAcesso());
    }
}
