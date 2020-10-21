package com.estacionamento.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "valores")
public class Valores implements Serializable{
	
   	private static final long serialVersionUID = 1L;
	
   	@Id
   	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
   	
   	@Column(name = "minutagem", nullable = false)
	private int minutagem;
   	
   	@Column(name = "valor", nullable = false)
	private double valor;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMinutagem() {
		return minutagem;
	}

	public void setMinutagem(int minutagem) {
		this.minutagem = minutagem;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "Valores [id=" + id + ", minutagem=" + minutagem + ", valor=" + valor + "]";
	}
   	
   	
}
