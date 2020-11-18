package com.estacionamento.api.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class UsuarioDto {

	private String id;

	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 5, max = 100, message = "Nome deve conter entre 5 e 100 caracteres.")
	private String nome;

	@NotEmpty(message = "Email não pode ser vazio.")
	@Email(message = "Email inválido.")
	private String email;

	@NotEmpty(message = "Senha não pode ser vazio.")
	@Length(min = 8, max = 25, message = "Senha deve conter entre 8 e 25 caracteres.")
	private String senha;

	@NotEmpty(message = "Telefone não pode ser vazio.")
	@Length(min = 10, max = 11, message = "O telefone deve conter entre 10 e 11 caracteres.")
	private String telefone;
	
	private String tipo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "UsuarioDto [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", telefone="
				+ telefone + "]";
	}

}
