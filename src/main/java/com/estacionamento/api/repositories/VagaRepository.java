package com.estacionamento.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.estacionamento.api.entities.Vaga;

public interface VagaRepository extends JpaRepository<Vaga, Integer>{
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Vaga SET disponivel = :situacao WHERE id = :idvaga")
	void alterarDisponibilidade(@Param("situacao") boolean situacao, @Param("idvaga") int idvaga);

}
