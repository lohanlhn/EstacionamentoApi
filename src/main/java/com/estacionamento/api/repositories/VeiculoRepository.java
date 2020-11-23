package com.estacionamento.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.estacionamento.api.entities.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Integer>{
	
	@Transactional(readOnly = true)
	@Query("SELECT ud FROM Veiculo ud WHERE ud.usuario.id = :usuarioId")
	Optional<List<Veiculo>> findByUsuarioId(int usuarioId);

}
