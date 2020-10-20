package com.estacionamento.api.dtos;

public class ValoresDto {
	private String id;
	private String minutagem;
	private String valor;
	
	public void setId(String id) {
		this.id = id;
	}
	public void setMinutagem(String minutagem) {
		this.minutagem = minutagem;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
		
	public String getId() {
		return id;
	}
	public String getMinutagem() {
		return minutagem;
	}
	public String getValor() {
		return valor;
	}
	@Override
	public String toString() {
		return "ValoresDto [id=" + id + ", minutagem=" + minutagem + ", valor=" + valor + "]";
	}
	
	
}
