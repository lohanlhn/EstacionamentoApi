package com.estacionamento.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estacionamento.api.entities.VagaOcupada;

public interface VagaOcupadaRepository extends JpaRepository<VagaOcupada, Integer>{

}
