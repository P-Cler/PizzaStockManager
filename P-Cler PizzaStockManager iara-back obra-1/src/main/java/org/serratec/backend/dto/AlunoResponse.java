package org.serratec.backend.dto;

public record AlunoResponse(Long id, String nome, String codMatricula, String email, boolean primeiroAcesso) {

}
