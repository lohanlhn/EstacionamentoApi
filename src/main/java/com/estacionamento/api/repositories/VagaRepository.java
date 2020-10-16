package com.estacionamento.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estacionamento.api.entities.Vaga;

public interface VagaRepository extends JpaRepository<Vaga, Integer>{

}
