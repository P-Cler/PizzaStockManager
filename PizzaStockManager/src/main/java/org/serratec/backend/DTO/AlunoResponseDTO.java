package org.serratec.backend.DTO;

import org.serratec.backend.entity.Aluno;

public class AlunoResponseDTO {

    private Long id;
    private String nome;
    private String codMatricula;
    private String email;
    private boolean primeiroAcesso;

    public AlunoResponseDTO(Aluno aluno) {
        this.id = aluno.getId();
        this.nome = aluno.getNome();
        this.codMatricula = aluno.getCodMatricula();
        this.email = aluno.getUsuario().getEmail();
        this.primeiroAcesso = aluno.getPrimeiroAcesso();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

	public String getCodMatricula() {
		return codMatricula;
	}

	public void setCodMatricula(String codMatricula) {
		this.codMatricula = codMatricula;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isPrimeiroAcesso() {
		return primeiroAcesso;
	}

	public void setPrimeiroAcesso(boolean primeiroAcesso) {
		this.primeiroAcesso = primeiroAcesso;
	}
    
    
    
}