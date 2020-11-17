package com.estacionamento.api.dtos;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class VeiculoDto {
	
	private String id;
	
	@NotEmpty(message = "Marca não pode ser vazio.")
	private String marca;
	
	@NotEmpty(message = "Cor não pode ser vazio.")
	private String cor;
	
	@NotEmpty(message = "Placa não pode ser vazio.")
	@Length(min = 7, max = 7 , message = "A placa deve conter apenas 7 digitos"  )
	private String placa;
	
	@NotEmpty(message = "Tipo não pode ser vazio.")
	private String tipo;
	
	@NotEmpty(message = "Id do Cliente não pode ser vazio.")
	private String usuarioId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}

	@Override
	public String toString() {
		return "VeiculoDto [id=" + id + ", marca=" + marca + ", cor=" + cor + ", placa=" + placa + ", tipo=" + tipo
				+ ", usuarioId=" + usuarioId + "]";
	}


	
	
	
}
