package com.estacionamento.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.estacionamento.api.entities.Veiculo;

@Transactional(readOnly = true)
public interface VeiculoRepository extends JpaRepository<Veiculo, Integer>{
	
	List<Veiculo> findBycliente_id(@Param("clienteid") int clienteId);

}
