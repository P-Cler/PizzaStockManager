package org.serratec.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank
    private String nome;

    @Column(name = "cod_matricula", nullable = false, unique = true, length = 20)
    @NotBlank
    private String codMatricula;

    @Column(nullable = false, unique = true, length = 120)
    @Email
    @NotBlank
    private String email;

    @Column(nullable = false)
    private String senhaHash;

    @Column(nullable = false)
    private Boolean primeiroAcesso = true;

    @ManyToMany
    @JoinTable(name = "aluno_turma",
            joinColumns = @JoinColumn(name = "aluno_id"),
            inverseJoinColumns = @JoinColumn(name = "turma_id"))
    private Set<Turma> turmas = new HashSet<>();
}
