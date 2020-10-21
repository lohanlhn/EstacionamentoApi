package com.estacionamento.api.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "vagaocupada")
public class VagaOcupada implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "horaentrada", nullable = false)
	private Date horaEntrada;
	
	@Column(name = "horasaida")
	private Date horaSaida;
	
	@Column(name = "valor")
	private double valor;
	
	@Column(name = "paga", nullable = false)
	private boolean paga;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	private Vaga vaga;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	private Veiculo veiculo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(Date horaEntrada) {
		this.horaEntrada = horaEntrada;
	}

	public Date getHoraSaida() {
		return horaSaida;
	}

	public void setHoraSaida(Date horaSaida) {
		this.horaSaida = horaSaida;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public boolean isPaga() {
		return paga;
	}

	public void setPaga(boolean paga) {
		this.paga = paga;
	}

	public Vaga getVaga() {
		return vaga;
	}

	public void setVaga(Vaga vaga) {
		this.vaga = vaga;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	@Override
	public String toString() {
		return "VagaOcupada [id=" + id + ", horaEntrada=" + horaEntrada + ", horaSaida=" + horaSaida + ", valor="
				+ valor + ", paga=" + paga + ", vaga=" + vaga + ", veiculo=" + veiculo + "]";
	}
	
	
}
