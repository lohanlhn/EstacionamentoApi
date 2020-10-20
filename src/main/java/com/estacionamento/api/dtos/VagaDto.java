package com.estacionamento.api.dtos;

public class VagaDto {
	private String id;
	private String codVaga;
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
