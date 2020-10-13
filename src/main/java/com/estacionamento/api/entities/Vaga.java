package com.estacionamento.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Vaga")
public class Vaga implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "patio", length = 100, nullable = false)
	private String patio;

	@Column(name = "disponivel", nullable = false)
	private boolean disponivel;

	@Column(name = "codVaga", nullable = false, length = 3, unique = true)
	private String codVaga;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPatio() {
		return patio;
	}

	public void setPatio(String patio) {
		this.patio = patio;
	}

	public boolean isDisponivel() {
		return disponivel;
	}

	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}

	public String getCodVaga() {
		return codVaga;
	}

	public void setCodVaga(String codVaga) {
		this.codVaga = codVaga;
	}

	@Override
	public String toString() {
		return "Vaga [id=" + id + ", patio=" + patio + ", disponivel=" + disponivel + ", codVaga=" + codVaga + "]";
	}

}
