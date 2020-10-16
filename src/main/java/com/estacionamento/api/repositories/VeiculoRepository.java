package com.estacionamento.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estacionamento.api.entities.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Integer>{

}
