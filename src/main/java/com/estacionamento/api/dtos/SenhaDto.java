package com.estacionamento.api.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class SenhaDto {

	@NotEmpty(message = "Email não pode ser vazio.")
	@Email(message = "Email inválido.")
	private String email;

	@NotEmpty(message = "Senha atual não pode ser vazio.")
	@Length(min = 8, max = 25, message = "Senha atual deve conter entre 8 e 25 caracteres.")
	private String senhaAtual;

	@NotEmpty(message = "Nova senha não pode ser vazio.")
	@Length(min = 8, max = 25, message = "Nome deve conter entre 8 e 25 caracteres.")
	private String novaSenha;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	@Override
	public String toString() {
		return "SenhaDto [email=" + email + ", senhaAtual=" + senhaAtual + ", novaSenha=" + novaSenha + "]";
	}

}
