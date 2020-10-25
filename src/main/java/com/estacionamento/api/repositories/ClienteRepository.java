package com.estacionamento.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.estacionamento.api.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	@Transactional(readOnly = true)
	@Query("SELECT cli WHERE Cliente cli.cpf = :cpf")
	Optional<Cliente> findByCpf(@Param("cpf") String cpf);

}
