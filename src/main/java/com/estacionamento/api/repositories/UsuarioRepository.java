package com.estacionamento.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estacionamento.api.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

}
