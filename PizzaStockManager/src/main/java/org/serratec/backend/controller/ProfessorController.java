package org.serratec.backend.controller;

import org.serratec.backend.DTO.ProfessorRequestDTO;
import org.serratec.backend.DTO.ProfessorResponseDTO;
import org.serratec.backend.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @PostMapping
    public ResponseEntity<ProfessorResponseDTO> criarProfessor(@RequestBody ProfessorRequestDTO request) {
        ProfessorResponseDTO response = professorService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
}