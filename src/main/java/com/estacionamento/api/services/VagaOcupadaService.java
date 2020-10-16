package com.estacionamento.api.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estacionamento.api.entities.VagaOcupada;
import com.estacionamento.api.repositories.VagaOcupadaRepository;
import com.estacionamento.api.utils.ConsistenciaException;

@Service
public class VagaOcupadaService {

	private static final Logger log = LoggerFactory.getLogger(VagaOcupadaService.class);

	@Autowired
	private VagaOcupadaRepository vagaOcupadaRepository;

	public Optional<VagaOcupada> buscarPorId(int id) throws ConsistenciaException {
		log.info("Service: buscando um vagaOcupada com o id: {}", id);

		Optional<VagaOcupada> vagaOcupada = vagaOcupadaRepository.findById(id);

		if (!vagaOcupada.isPresent()) {
			log.info("Service: Nenhum vagaOcupada com id: {} foi encontrado", id);
			throw new ConsistenciaException("Nenhum vagaOcupada com id: {} foi encontrado", id);
		}

		return vagaOcupada;

	}

	public VagaOcupada salvar(VagaOcupada vagaOcupada) throws ConsistenciaException {
		log.info("Sevice: salvando o vagaOcupada: {}", vagaOcupada);

		if (vagaOcupada.getId() > 0)
			buscarPorId(vagaOcupada.getId());

		return vagaOcupadaRepository.save(vagaOcupada);

	}
}
