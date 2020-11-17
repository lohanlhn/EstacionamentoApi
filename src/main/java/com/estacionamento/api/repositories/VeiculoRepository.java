package com.estacionamento.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.estacionamento.api.entities.Veiculo;

@Transactional(readOnly = true)
public interface VeiculoRepository extends JpaRepository<Veiculo, Integer>{
	
	@Transactional(readOnly = true)
	Optional<List<Veiculo>> findByUsuarioId(int usuarioId);

}
