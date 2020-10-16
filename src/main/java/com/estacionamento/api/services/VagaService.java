package com.estacionamento.api.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.estacionamento.api.entities.Vaga;
import com.estacionamento.api.repositories.VagaRepository;
import com.estacionamento.api.utils.ConsistenciaException;

@Service
public class VagaService {
	private static final Logger log = LoggerFactory.getLogger(VagaService.class);

	@Autowired
	private VagaRepository vagaRepository;

	public Optional<Vaga> buscarPorId(int id) throws ConsistenciaException {
		log.info("Service: buscando um vaga com o id: {}", id);

		Optional<Vaga> vaga = vagaRepository.findById(id);

		if (!vaga.isPresent()) {
			log.info("Service: Nenhum usuário com id: {} foi encontrado", id);
			throw new ConsistenciaException("Nenhum usuário com id: {} foi encontrado", id);
		}

		return vaga;

	}

	public Vaga salvar(Vaga vaga) throws ConsistenciaException {
		log.info("Sevice: salvando o vaga: {}", vaga);

		if (vaga.getId() > 0)
			buscarPorId(vaga.getId());

		try {
			return vagaRepository.save(vaga);
		} catch (DataIntegrityViolationException e) {
			log.info("Service: O codVaga: {} já está cadastrado para outra vaga", vaga.getCodVaga());
			throw new ConsistenciaException("O CodVaga: {} já está cadastrado para outra vaga", vaga.getCodVaga());
		}

	}
}
