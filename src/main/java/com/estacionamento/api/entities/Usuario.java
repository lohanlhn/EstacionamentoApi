package com.estacionamento.api.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


import javax.persistence.JoinColumn;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
   	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "nome", nullable = false, length = 100)
	private String nome;
	
	@Column(name = "email", nullable = false, length = 100,unique = true)
	private String email;
	
	@Column(name = "senha", nullable = false, length = 100)
	private String senha;
	
	@Column(name = "tipo", nullable = false)
	private String tipo;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
   	@JoinTable(name = "Usuario_Regra",
   	           joinColumns = { @JoinColumn(name = "usuario_id") },
   	           inverseJoinColumns = { @JoinColumn(name = "regra_id") })
   	private List<Regra> regras;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Regra> getRegras() {
		return regras;
	}

	public void setRegras(List<Regra> regras) {
		this.regras = regras;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", tipo=" + tipo
				+ ", regras=" + regras + "]";
	}
	
}
