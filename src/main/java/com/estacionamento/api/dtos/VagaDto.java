package com.estacionamento.api.dtos;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;


public class VagaDto {
	private String id;
	
	@NotEmpty(message= "codVaga não pode ser vazio")
	@Length(min = 3, max = 3, message = "codVaga deve ter 3 caracteres")
	private String codVaga;
	
	@NotEmpty(message= "Disponivel não pode ser vazio")
	private String disponivel;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCodVaga() {
		return codVaga;
	}
	public void setCodVaga(String codVaga) {
		this.codVaga = codVaga;
	}
	public String getDisponivel() {
		return disponivel;
	}
	public void setDisponivel(String disponivel) {
		this.disponivel = disponivel;
	}
	@Override
	public String toString() {
		return "VagaDto [id=" + id + ", codVaga=" + codVaga + ", disponivel=" + disponivel + "]";
	}
	
	
}
