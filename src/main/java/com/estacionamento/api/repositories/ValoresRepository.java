package com.estacionamento.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estacionamento.api.entities.Valores;

public interface ValoresRepository extends JpaRepository<Valores, Integer>{

}
