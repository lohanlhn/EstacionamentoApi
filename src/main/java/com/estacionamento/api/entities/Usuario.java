package com.estacionamento.api.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.PrePersist;
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
	
	@Column(name = "dataacesso", nullable = false)
	private Date dataAcesso;
	
	@Column(name = "ativo", nullable = false)
	private boolean ativo;
	
//	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private Cliente cliente;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

	public Date getDataAcesso() {
		return dataAcesso;
	}

	public void setDataAcesso(Date dataAcesso) {
		this.dataAcesso = dataAcesso;
	}

	public boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	@PrePersist
   	public void prePersist() {
         	dataAcesso = new Date();
   	}

//	public Cliente getCliente() {
//		return cliente;
//	}
//
//	public void setCliente(Cliente cliente) {
//		this.cliente = cliente;
//	}

	public List<Regra> getRegras() {
		return regras;
	}

	public void setRegras(List<Regra> regras) {
		this.regras = regras;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", tipo=" + tipo
				+ ", dataAcesso=" + dataAcesso + ", ativo=" + ativo + "]";
	}
	
	
}
