package com.estacionamento.api.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.estacionamento.api.entities.VagaOcupada;

public interface VagaOcupadaRepository extends JpaRepository<VagaOcupada, Integer>{
	
	
	@Transactional
   	@Modifying(clearAutomatically = true)
   	@Query("UPDATE VagaOcupada SET horaSaida = :novaHoraSaida WHERE id = :id")
	void alterarHoraSaida(@Param("novaHoraSaida") Date novaHoraSaida, @Param("id") int id);
	
	@Transactional
   	@Modifying(clearAutomatically = true)
   	@Query("UPDATE VagaOcupada SET valor = :novoValor WHERE id = :id")
	void alterarValor(@Param("novoValor") double valor, @Param("id") int id);
	
	@Transactional(readOnly = true)
	@Query(value = "SELECT * FROM vagaocupada WHERE paga = 0", nativeQuery = true)
	Optional<List<VagaOcupada>> buscarVagaNaoPagas();
	

}
