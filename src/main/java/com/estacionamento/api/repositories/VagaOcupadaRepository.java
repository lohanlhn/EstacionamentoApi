package com.estacionamento.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.estacionamento.api.entities.VagaOcupada;

public interface VagaOcupadaRepository extends JpaRepository<VagaOcupada, Integer>{
	
	@Transactional
	@Query("SELECT vo FROM VagaOcupada WHERE vo.id = :id")
	Optional<VagaOcupada> findById(@Param("id") int id);

}
