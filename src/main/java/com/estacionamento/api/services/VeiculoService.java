package com.estacionamento.api.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.estacionamento.api.entities.Veiculo;
import com.estacionamento.api.repositories.VeiculoRepository;
import com.estacionamento.api.utils.ConsistenciaException;

@Service
public class VeiculoService {
	private static final Logger log = LoggerFactory.getLogger(VeiculoService.class);

	@Autowired
	private VeiculoRepository veiculoRepository;

	public Optional<Veiculo> buscarPorId(int id) throws ConsistenciaException {
		log.info("Service: buscando um veiculo com o id: {}", id);

		Optional<Veiculo> veiculo = veiculoRepository.findById(id);

		if (!veiculo.isPresent()) {
			log.info("Service: Nenhum veiculo com id: {} foi encontrado", id);
			throw new ConsistenciaException("Nenhum veiculo com id: {} foi encontrado", id);
		}

		return veiculo;

	}
	
	@Cacheable("cacheVeiculosPorCliente")
	public Optional<List<Veiculo>> buscarPorClienteId (int clienteId) throws ConsistenciaException {
		
		log.info("Service: Buscando os veiculos do cliente de id: {}", clienteId);
		Optional<List<Veiculo>> veiculos = Optional.ofNullable(veiculoRepository.findByClienteId(clienteId));
		
		if(!veiculos.isPresent() || veiculos.get().size() < 1) {
			log.info("Service: Nenhum veiculo encontrado do cliente de Id: {}", clienteId);
			throw new ConsistenciaException("Nenhum veiculo encontrado do cliente de Id: {}", clienteId);
			
		}
		
		return veiculos;
		
	}

	public Veiculo salvar(Veiculo veiculo) throws ConsistenciaException {
		log.info("Sevice: salvando o veiculo: {}", veiculo);

		if (veiculo.getId() > 0)
			buscarPorId(veiculo.getId());

		try {
			return veiculoRepository.save(veiculo);
		} catch (DataIntegrityViolationException e) {
			log.info("Service: A placa: {} já está cadastrado para outra placa", veiculo.getPlaca());
			throw new ConsistenciaException("O CodVaga: {} já está cadastrado para outra vaga", veiculo.getPlaca());
		}

	}
	
	@CachePut("cacheVeiculosPorCliente")
	public void excluirPorId(int id) throws ConsistenciaException {
		log.info("Service: excluindo veiculo de Id: {}", id);
		buscarPorId(id);
		
		veiculoRepository.deleteById(id);
	}
}
