package com.estacionamento.api.dtos;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;


public class VagaOcupadaDto {
	
	private String id;
	
	private String horaEntrada;
	
	private String horaSaida;
	
	private String valor;
	
	private String paga;
	
	@Length(min = 1, message = "O id da vaga deve ter ao minimo 1 digito")
	@NotEmpty(message = "O id da vaga não pode ser vazio.")
	private String vagaId;
	
	@Length(min = 1, message = "O id do veiculo deve ter ao minimo 1 digito")
	@NotEmpty(message = "O id do veiculo não pode ser vazio")
	private String veiculoId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(String horaEntrada) {
		this.horaEntrada = horaEntrada;
	}

	public String getHoraSaida() {
		return horaSaida;
	}

	public void setHoraSaida(String horaSaida) {
		this.horaSaida = horaSaida;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getPaga() {
		return paga;
	}

	public void setPaga(String paga) {
		this.paga = paga;
	}

	public String getVaga() {
		return vagaId;
	}

	public void setVaga(String vaga) {
		this.vagaId = vaga;
	}

	public String getVeiculo() {
		return veiculoId;
	}

	public void setVeiculo(String veiculo) {
		this.veiculoId = veiculo;
	}
	
	@Override
	public String toString() {
		return "VagaOcupadaDto [id= " + id + ", horaEntrada= " + horaEntrada + ", horaSaida= " + horaSaida +
				", valor= " + valor + ", paga= " + paga + ", vaga= " + vagaId + ", veiculo= " + veiculoId 
				+ "]";
	}

}
