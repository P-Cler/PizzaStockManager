package org.serratec.backend.controller;

import org.serratec.backend.DTO.AlunoRequest;
import org.serratec.backend.DTO.AlunoResponse;
import org.serratec.backend.DTO.LoginRequest;
import org.serratec.backend.DTO.LoginResponse;
import org.serratec.backend.service.AlunoService;
import org.serratec.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

	@Autowired
    private AuthService authService;
	
	@Autowired
    private AlunoService alunoService;

    @PostMapping("/alunos")
    public ResponseEntity<AlunoResponse> criar(@RequestBody AlunoRequest request) {
        AlunoResponse response = alunoService.criar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/alunos/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        alunoService.deletar(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

}
