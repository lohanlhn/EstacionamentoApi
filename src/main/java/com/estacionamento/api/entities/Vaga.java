package com.estacionamento.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vaga")
public class Vaga implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "codvaga", length = 3, nullable = false, unique = true)
	private String codVaga;
	
	@Column(name = "disponivel", nullable = false)
	private boolean disponivel;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodVaga() {
		return codVaga;
	}

	public void setCodVaga(String codVaga) {
		this.codVaga = codVaga;
	}

	public boolean getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}
	
	

	@Override
	public String toString() {
		return "Vaga [id=" + id + ", codVaga=" + codVaga + ", disponivel=" + disponivel + "]";
	}
	
	
}
